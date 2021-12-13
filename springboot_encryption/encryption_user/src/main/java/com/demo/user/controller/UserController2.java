package com.demo.user.controller;

import com.demo.token.config.RedisService;
import com.demo.token.util.EncryptUtil;
import com.demo.token.util.PublicMethodUtils;
import com.demo.token.util.PublicMethodUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lkz
 * @date 2021/12/4 10:58
 * @description
 */
@RestController
public class UserController2 {

    @Autowired
    RedisService redisService;
    //请求公钥
    @GetMapping("getPublicKey")
    public String testHttp(){
        Map<String, byte[]> user = redisService.getCacheMap("userKeyPair");
        //对方的公钥
        String url="localhost:1112/returnPublicKey";
        String oppoPublicKey = PublicMethodUtils2.getPublicKey(user, url);
        //对方的公钥保存起来
        redisService.setCacheObject("user:consumerPublicKey",oppoPublicKey);
        return "user---->"+oppoPublicKey;
    }
    // 处理对方获取公钥的请求
    @GetMapping("returnPublicKey")
    public String test(HttpServletRequest request){
        //取出所带的公钥 生成自己的本地密钥
        String oppoPublicKey = request.getHeader("publicKey");
        redisService.setCacheObject("consumer:userPublicKey",oppoPublicKey);
        System.out.println("对方的公钥"+oppoPublicKey);
        Map<String, byte[]> userKey = redisService.getCacheMap("userKeyPair");
        //获取自己的公钥
        String publicKey = PublicMethodUtils.returnPublic(userKey);
        // 返回给对方自己的公钥
        return publicKey;
    }

    //请求获取token
    @PostMapping("getToken")
    public String getToken(HttpServletRequest request) throws Exception {
        String userAesSecretKey = redisService.getCacheObject("userAesSecretKey");
        Map<String,Object> serverInfo=new ConcurrentHashMap<>();
        serverInfo.put("appId","user");
        serverInfo.put("app_secret","user:app_secret");
        serverInfo.put("tenant_id","user_110");
        long apply_time=System.currentTimeMillis();
        serverInfo.put("apply_time",apply_time);
        serverInfo.put("aesKey",userAesSecretKey);
        serverInfo.put("oppoPublicKey",redisService.getCacheObject("user:consumerPublicKey"));
        Map<String, byte[]> userKey = redisService.getCacheMap("userKeyPair");
        String url="localhost:1112/generateToken";
        String token = PublicMethodUtils.getToken(userKey, url, serverInfo, userAesSecretKey);
        System.out.println("user系统获取的token:"+token);

        return token;

    }

    //生成token
    @GetMapping("generateToken")
    public String generateToken(HttpServletRequest request) throws Exception {
        String message = request.getHeader("message");
        Map<String, byte[]> consumer = redisService.getCacheMap("consumer");
        String oppoPublicKey = redisService.getCacheObject("consumer:userPublicKey");
        Map map = PublicMethodUtils.getInstance().generateToken(message, consumer, oppoPublicKey);
        String access_token = (String)map.get("access_token");//token本地存储一份 验证使用
        String tokenMassage = (String)map.get("tokenMessage");// 返回请求报文
        redisService.setCacheObject("access_token",access_token);
        return tokenMassage;
    }

    @PostMapping("checkToken")
    public String checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        //截取
        String[] split = token.split("&");
        //验签
        boolean b1 = EncryptUtil.getInstance().verifySignature(split[0], split[1]);

        System.out.println("系统获取的token:--"+split[0]);


        return "0000";
    }


}
