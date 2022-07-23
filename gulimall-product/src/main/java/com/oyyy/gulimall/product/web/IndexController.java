package com.oyyy.gulimall.product.web;

import com.oyyy.gulimall.product.entity.CategoryEntity;
import com.oyyy.gulimall.product.service.CategoryService;
import com.oyyy.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 跳转首页并且查询一级分类
     * @param model
     * @return
     */
    @GetMapping(value = {"/","index.html"})
    private String indexPage(Model model) {
        //1、查出所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Catagories();
        model.addAttribute("categories",categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping(value = "/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        Map<String, List<Catelog2Vo>> map = categoryService.getCatalogJson();
        return map;
    }


    @Autowired
    RedissonClient redisson;

    //可重入锁
    @ResponseBody
    @GetMapping("/hello")
    public String hello (){
        //1、获取一把锁，只要锁的名字一样，就是同一把锁
        RLock lock = redisson.getLock("my-lock");


        //最佳实战
        //（1）lock.lock(10, TimeUnit.SECONDS);省掉了整个续期操作。手动解锁
        //2、加锁
        lock.lock();//阻塞式等待，默认加的锁都是30s时间
        //（1）锁的自动续期，如果业务超长，运行期间自动给锁续上新的30s。不用担心业务时间长，锁自动过期被删掉
        //（2）加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁，锁默认在30s以后自动删除。
        try {
            System.out.println("加锁成功，执行业务..."+Thread.currentThread().getId());
            Thread.sleep(30000);
        }catch (Exception e){

        }finally {
            //3、解锁 假设解锁代码没有运行，redisson会不会出现死锁
            System.out.println("释放锁..."+Thread.currentThread().getId());
            lock.unlock();
        }
        return "hello";
    }

    //保证一定能读到最新数据，修改其间，写锁是一个排它锁（互斥锁，独享锁）。读锁是一个共享锁
    //写锁没释放读就必须等待
    //读 + 读：相当于无锁，并发读，只会在redis中记录好，所有当前的读锁。他们都会同时加锁成功
    //写 + 读：等待写锁释放
    //写 + 写：阻塞方式
    //读 + 写：有读锁。写也需要等待
    //只要有些写的存在，都必须等到
    @GetMapping("/write")
    @ResponseBody
    public String writeValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        //加写锁
        RLock rLock = lock.writeLock();
        try {
            //1、改数据加写锁，度读数据加读锁
            rLock.lock();
            System.out.println("写锁加锁成功..."+Thread.currentThread().getId());
            s = UUID.randomUUID().toString();
            Thread.sleep(30000);
            redisTemplate.opsForValue().set("writeValue",s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("写锁释放..."+Thread.currentThread().getId());
        }
        return s;
    }

    @GetMapping("/read")
    @ResponseBody
    public String readValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        //加读锁
        RLock rLock = lock.readLock();
        try {
            rLock.lock();
            System.out.println("读锁加锁成功..."+Thread.currentThread().getId());
            s = redisTemplate.opsForValue().get("writeValue");
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
            rLock.unlock();
            System.out.println("读锁释放..."+Thread.currentThread().getId());
        }
        return s;
    }

    /**
     * 车库停车
     * 3车位
     * 信号量也可以用作分布式限流
     */
    @GetMapping("/park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        //park.acquire();//获取一个信号，获取一个值，占一个车位。阻塞方等待
        boolean b = park.tryAcquire(); //非阻塞等待
        if(b){
            //执行业务
        }else{
            return "error";
        }
        return "ok=>"+b;
    }

    @GetMapping("/go")
    @ResponseBody
    public String go(){
        RSemaphore park = redisson.getSemaphore("park");
        park.release();//释放一个车位
        return "ok";
    }

    /**
     * 放假，锁门
     * 1班没人了，2
     * 5个班全部走完，我们可以锁大门
     */
    @GetMapping("/lockDoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);
        door.await();//等待闭锁都完成

        return "放假了...";
    }

    @GetMapping("/gogogo/{id}")
    @ResponseBody
    public String gogogo(@PathVariable("id") Long id){
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();//计数减1
        return id+"班的人都走了";
    }
}
