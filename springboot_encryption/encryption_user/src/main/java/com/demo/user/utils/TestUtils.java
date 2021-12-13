package com.demo.user.utils;

import com.demo.token.config.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lkz
 * @date 2021/12/8 9:33
 * @description
 */
@Component
public class TestUtils {

    @Value("${service.appId}")
    private static String service;


    public static String getService() {
        return service;
    }

    public static void setService(String service) {
        TestUtils.service = service;
    }

    @Autowired
    private static RedisService redisService;

    public static String getRedis(){


        return "redis";
    }
}
