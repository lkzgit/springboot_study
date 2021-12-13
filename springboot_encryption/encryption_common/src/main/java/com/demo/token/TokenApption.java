package com.demo.token;//package com.demo.token;
//
//import com.demo.token.config.RedisService;
//import com.demo.token.util.DHUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import javax.annotation.PostConstruct;
//import java.util.Map;
//
///**
// * @author lkz
// * @date 2021/12/2 16:53
// * @description
// */
//@SpringBootApplication
//public class TokenApption {
//
//    public static void main(String[] args) {
//        SpringApplication.run(TokenApption.class,args);
//    }
//
////    @Autowired
////    RedisService redisService;
////
////    @PostConstruct
////    public void generateDHKeys(){
////        Map<String, byte[]> key = DHUtil.getKey();
////        Map<String, byte[]> key2 = DHUtil.getKey();
////        redisService.setCacheMap("token1",key);
////        redisService.setCacheMap("token2",key2);
////    }
//}
