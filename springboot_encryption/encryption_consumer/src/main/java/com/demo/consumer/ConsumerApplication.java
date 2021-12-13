package com.demo.consumer;

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
 * @date 2021/12/4 10:53
 * @description
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.demo.token.config","com.demo.consumer"})
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);

    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
        @Autowired
        RedisService redisService;

        @PostConstruct
        public void generateDHKeys(){
            Map<String, byte[]> key = DHUtil.getKey();
            String aesSecretKey = AESUtil.generateAESKey();
            redisService.setCacheMap("consumerKeyPair",key);
            redisService.setCacheObject("consumerAesSecretKey",aesSecretKey);

        }

}
