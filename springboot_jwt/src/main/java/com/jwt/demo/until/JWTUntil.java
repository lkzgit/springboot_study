package com.jwt.demo.until;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUntil {

    //设置秘钥
    private static String TOKEN="token!kk";

    /**
     * 生成token
     * @param map
     * @return
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,60);//默认五分钟
        String sign = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(TOKEN));
        return sign;
    }

    /**
     * 验证token
     * @param token
     * 如果验证部通过则会有异常抛出
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);

    }

    /**
     * 获取token中的payLoad
     * @param token
     * @return
     *
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }
}
