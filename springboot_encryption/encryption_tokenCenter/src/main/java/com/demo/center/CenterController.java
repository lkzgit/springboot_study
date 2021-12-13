package com.demo.center;

import cn.hutool.core.io.IoUtil;
import com.demo.token.config.RedisService;
import com.demo.token.util.PublicMethodUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName CenterController.java
 * @Description 认证服务中心
 * @createTime 2021年12月06日 22:29:00
 */
@RestController
public class CenterController {

    private static final ThreadLocal<Map<String,byte[]>> threadMap=new ThreadLocal<Map<String, byte[]>>();

    @Autowired
    RedisService redisService;

    @GetMapping("returnPublicKey")
    public String returnPublicKey(HttpServletRequest request){
        //获取请求头中的公钥
        String oppoPublicKey = request.getHeader("publicKey");
        System.out.println("请求头中获取的公钥:"+oppoPublicKey);
        String service = request.getHeader("service");
        redisService.setCacheObject("center:"+service+"PublicKey",oppoPublicKey);
        Map<String, byte[]> centerKeyPair = redisService.getCacheMap("centerKeyPair");
        String selfPublicKey = Base64.encodeBase64String(centerKeyPair.get("publicKey"));
        return  selfPublicKey;

    }

    @PostMapping("generateToken")
    public  Map generateToken(HttpServletRequest request){
        String service = request.getHeader("service");
        String message = request.getHeader("message");
        Map<String, byte[]> centerKeyPair = redisService.getCacheMap("centerKeyPair");
        String oppoKey="center:"+service+"PublicKey";
        String oppoPublicKey = redisService.getCacheObject(oppoKey);
        Map map = PublicMethodUtils.getInstance().generateToken(message, centerKeyPair, oppoPublicKey);
        String access_token = (String)map.get("access_token");//token本地存储一份 验证使用
        String tokenMassage = (String)map.get("tokenMessage");// 返回请求报文
        redisService.setCacheObject("access_token",access_token);
        return map;
    }

    @GetMapping("checkToken")
    public String checkToken(HttpServletRequest request){
        String access_token = request.getHeader("access_token");
        System.out.println("access_token:"+access_token);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String body = IoUtil.read(reader);
            System.out.println("返回json数据");
            System.out.println(body);
        }catch (Exception e){
            System.out.println("错误信息");
        }
        return "00000";
    }


}
