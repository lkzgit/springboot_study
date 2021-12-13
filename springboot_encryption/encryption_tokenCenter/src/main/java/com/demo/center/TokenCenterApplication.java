package com.demo.center;

import com.demo.token.config.RedisService;
import com.demo.token.util.AESUtil;
import com.demo.token.util.DHUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author lkz
 * @date 2021/12/13 20:16
 * @description
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.demo.token.config","com.demo.center"})
public class TokenCenterApplication {




    public static void main(String[] args) {
        SpringApplication.run(TokenCenterApplication.class,args);
    }


    @Autowired
    RedisService redisService;

    @PostConstruct
    public void generateDHKeys(){
        Map<String, byte[]> key = DHUtil.getKey();
        redisService.setCacheMap("centerKeyPair",key);
        String aesSecretKey = AESUtil.generateAESKey();
        redisService.setCacheObject("centerAesSecretKey",aesSecretKey);
    }
}