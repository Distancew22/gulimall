package com.oyyy.gulimall.product;

//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSClientBuilder;
import com.oyyy.gulimall.product.entity.BrandEntity;
import com.oyyy.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

//    @Resource
//    OSSClient ossClient;

    @Test
    public void test() throws FileNotFoundException {
//        String endpoint = "oss-cn-guangzhou.aliyuncs.com";
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，https://ram.console.aliyun.com/users/gulimall/
//        String accessKeyId = "LTAI5t9A4dfMdkhFgX4Kpx2H";
//        String accessKeySecret = "Ms7I24BUivYMyW2upfQsuUvvc3qoWV";
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
//        InputStream inputStream = new FileInputStream("F:\\20220708170915.png");
//        ossClient.putObject("gulimall-yhht", "20220708170915.png", inputStream);
//        // 关闭OSSClient。
//        ossClient.shutdown();
//        System.out.println("上传成功");
    }

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("李为");
        this.brandService.save(brandEntity);
        System.out.println("保存成功。");
    }

}
