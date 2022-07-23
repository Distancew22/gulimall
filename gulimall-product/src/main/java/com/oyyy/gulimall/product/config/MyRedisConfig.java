package com.oyyy.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRedisConfig {
    // redission通过redissonClient对象使用
    // 如果是多个redis集群，可以配置
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        // 创建单例模式的配置
        config.useSingleServer().setAddress("redis://175.178.79.166:6379");
        // 根据Config创建出Redisson实例
        return Redisson.create(config);
    }

}
