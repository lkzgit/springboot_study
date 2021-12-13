package com.demo.user.controller;

import com.demo.token.config.RedisService;
import com.demo.token.util.PublicMethodUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lkz
 * @date 2021/12/4 10:58
 * @description
 */
@RestController
@RequestMapping("userThree")
public class UserController3 {

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
    @Value("${service.appId}")
    private String appId;


    //请求公钥
    @GetMapping("getPublicKey")
    public String testHttp(){
        Map<String, byte[]> user = redisService.getCacheMap("userKeyPair");
        //对方的公钥
        String url=http+returnPublicKeyUrl;
        String oppoPublicKey = PublicMethodUtils.getPublicKey(user, url,appId);
        //对方的公钥保存起来
        redisService.setCacheObject("user:centerPublicKey",oppoPublicKey);
        return "user---->"+oppoPublicKey;
    }

    //请求获取token
    @PostMapping("getToken")
    public String getToken() throws Exception {
        String userAesSecretKey = redisService.getCacheObject("userAesSecretKey");
        Map<String,Object> serverInfo=new ConcurrentHashMap<>();
        serverInfo.put("appId",appId);
        serverInfo.put("app_secret","user:app_secret");
        serverInfo.put("tenant_id","user_110");
        long apply_time=System.currentTimeMillis();
        serverInfo.put("apply_time",apply_time);
        serverInfo.put("oppoPublicKey",redisService.getCacheObject("user:centerPublicKey"));
        Map<String, byte[]> userKey = redisService.getCacheMap("userKeyPair");
        String url=http+generateTokenUrl;
        String token = PublicMethodUtils.getToken(userKey, url, serverInfo, userAesSecretKey);
        System.out.println("user系统获取的token:"+token);

        return token;

    }

    @GetMapping("testOver")
    public String testOver() throws Exception {
//        this.testHttp();
//        String token = this.getToken();
//        String body = HttpRequest.get("localhost:1112/conThree/getAskOne").
//                header("accessToken",token).header("sysTem","sysTem").execute().body();
//        System.out.println(body);
    String url="http://localhost:1112/conThree/getAskTwo";
        HttpHeaders headers = getDefaultHeaders();
        headers.set("tit","jjj");
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode params = objectMapper.createObjectNode();
        params.put("username", "buejee2");
        params.put("password", "123456");
        HttpEntity<String> request = new HttpEntity<String>(params.toString(),headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println(response);


        return "完成全部";
    }


    @GetMapping("testRest")
    public String testRest(){
       // String url="http://localhost:1112/conThree/getAskGet/"+"11";
        String url="http://localhost:1112/conThree/getAskGet?id={id}&name={name}";
        HttpHeaders headers = getDefaultHeaders();
        headers.set("token","844774744h");
        Map<String,String> map=new HashMap<>();
        map.put("id","11");
        map.put("name","1122");
        HttpEntity mapHttpEntity = new HttpEntity<String>(map.toString(),headers);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class, mapHttpEntity);
        System.out.println(forEntity.getBody());


        return "ok";
    }

    private static HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.set("Connection", "Close");
        headers.add("X-Auth-Token", UUID.randomUUID().toString());
        return headers;
    }






}
