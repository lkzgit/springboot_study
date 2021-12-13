package com.demo.user;

import com.demo.token.config.RedisService;
import com.demo.token.util.AESUtil;
import com.demo.token.util.DHUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author lkz
 * @date 2021/12/4 13:42
 * @description
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.demo.token.config","com.demo.user"})
public class UserApplication {


    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }


    @Autowired
    RedisService redisService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void generateDHKeys(){
        Map<String, byte[]> key = DHUtil.getKey();
        redisService.setCacheMap("userKeyPair",key);
        String aesSecretKey = AESUtil.generateAESKey();
        redisService.setCacheObject("userAesSecretKey",aesSecretKey);
    }
}
