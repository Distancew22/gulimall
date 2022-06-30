package com.oyyy.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 1、整合mybatis-plus
 *      <dependency>
 *          <groupId>com.baomidou</groupId>
 *          <artifactId>mybatis-plus-boot-starter</artifactId>
 *          <version>mybatis-plus-latest-version</version>
 *      </dependency>
 * 2、配置
 *      1、配置数据源
 *          1）导入数据库的驱动
 *          2）在application.yml，配置数据源相关信息
 *      2、配置Mybatis-plus
 *          1）使用@MMapperScan
 *          2）告诉Mybatis-plus sql映射文件位置
 *
 */
@MapperScan("com.oyyy.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
