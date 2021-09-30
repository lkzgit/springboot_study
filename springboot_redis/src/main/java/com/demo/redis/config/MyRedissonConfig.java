package com.demo.redis.config;

/**
 * @author lkz
 * @date 2021/9/28 15:10
 * @description
 */

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * redisson 使用
 */
@Configuration
public class MyRedissonConfig {

    /**
     * 所有对Redisson的使用都是通过RedissonClient
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod="shutdown")
    public Redisson redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://139.196.37.168:6379");
        config.useSingleServer().setPassword("123456");

        //2、根据Config创建出RedissonClient实例
        //Redis url should start with redis:// or rediss://
        Redisson redisson = (Redisson) Redisson.create(config);
        return redisson;
    }
}
