package com.demo.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.demo.token.config.RedisService;
import com.demo.token.util.AskTokenUtil;
import com.demo.token.util.PublicMethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lkz
 * @date 2021/12/4 10:58
 * @description
 */
@RestController
@RequestMapping("conThree")
public class ComsumerController3 {

    @Autowired
    RedisService redisService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${center.http}")
    private String http;
    @Value("${center.returnPublicKey}")
    private String returnPublicKeyUrl;
    @Value("${center.generateToken}")
    private String generateTokenUrl;



    /**
     * 获取公钥
     * @return
     */
    @PostMapping("getPublicKey")
    public String getPublicKey(){

        Map<String, byte[]> user = redisService.getCacheMap("consumerKeyPair");
        //对方的公钥
        String url=http+returnPublicKeyUrl;
        String oppoPublicKey = PublicMethodUtils.getPublicKey(user, url,"consumer");
        //对方的公钥保存起来
        redisService.setCacheObject("consumer:centerPublicKey",oppoPublicKey);
        return "user---->"+oppoPublicKey;

    }



    //密文方式返回
    @PostMapping("getToken")
    public String getToken(HttpServletRequest request) throws Exception {
        String userAesSecretKey = redisService.getCacheObject("consumerAesSecretKey");
        Map<String,Object> serverInfo=new ConcurrentHashMap<>();
        serverInfo.put("appId","consumer");
        serverInfo.put("app_secret","consumer:app_secret");
        serverInfo.put("tenant_id","consumer_110");
        long apply_time=System.currentTimeMillis();
        serverInfo.put("apply_time",apply_time);
        serverInfo.put("aesKey",userAesSecretKey);
        serverInfo.put("oppoPublicKey",redisService.getCacheObject("consumer:centerPublicKey"));
        Map<String, byte[]> userKey = redisService.getCacheMap("consumerKeyPair");
        String url=http+generateTokenUrl;
        String token = PublicMethodUtils.getToken(userKey, url, serverInfo, userAesSecretKey);
        System.out.println("consumer系统获取的token:"+token);
        return token;
    }

    @GetMapping("getAskOne")
    public String getAskOne(HttpServletRequest request){
        //String access_token = request.getHeader("access_token");
        String redis = AskTokenUtil.getInstance().getRedis();
        String key = redisService.getCacheObject("key");
        return "9999----token:"+key;
    }

    @PostMapping("getAskTwo")
    public String getAskgetAskTwo(HttpServletRequest request){
        String tit = request.getHeader("tit");
        System.out.println(tit);
        Map<String,String> map=new HashMap<>();
        map.put("err","12");
        map.put("msg","ok");

        return JSON.toJSONString(map);
    }

//    @GetMapping("getAskGet/{id}")
//    public String getAskgetAskGet(String id, HttpServletRequest request){
//        String tit = request.getHeader("tit");
//        System.out.println(tit);
//        System.out.println(id);
//        Map<String,String> map=new HashMap<>();
//        map.put("err","12");
//        map.put("msg","ok");
//
//        return JSON.toJSONString(map);
//    }

    @GetMapping("getAskGet")
    public String getAskgetAskGet(String id,String name, HttpServletRequest request){
        String tit = request.getHeader("token");
        System.out.println(tit);
        System.out.println(id);
        System.out.println(name);
        Map<String,String> map=new HashMap<>();
        map.put("err","12");
        map.put("msg","ok");

        return JSON.toJSONString(map);
    }



}
