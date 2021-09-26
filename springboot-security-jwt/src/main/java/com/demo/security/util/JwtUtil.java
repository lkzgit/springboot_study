package com.demo.security.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.demo.security.constants.Constant;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

/**
 * @author lkz
 */
public class JwtUtil {
    /**
     * 生成Token
     *
     * @param userId
     * @param account
     * @param grantedAuthorities
     * @return
     * @throws Exception
     */
    public static String createToken(String userId, String account, Set<GrantedAuthority> grantedAuthorities) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, Constant.JWT_TTL);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("userId", userId)
                .withClaim("account", account)
                .withClaim("authorities", JSON.toJSONString(grantedAuthorities))
                .withSubject("令牌")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(Constant.SECRET));//签名
        return token;
    }

    /**
     * 验证Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Constant.SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 解析Token
     *
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }

    public static void main(String[] args) throws InterruptedException {
        String token = JwtUtil.createToken("12345", "wangbo", null);
        System.out.println("token=" + token);
        //Thread.sleep(5000);
        Map<String, Claim> map = null;
        try {
            map = JwtUtil.verifyToken(token);
        } catch (TokenExpiredException e) {
            System.out.println("token已过期");
        } catch (SignatureVerificationException e) {
            System.out.println("Token无效");
        }
        //Map<String, Claim> map = JwtUtil.parseToken(token);
        //遍历
        for (Map.Entry<String, Claim> entry : map.entrySet()) {
            if ("userId".equals(entry.getKey()) || "account".equals(entry.getKey())) {
                System.out.println(entry.getKey() + "============" + entry.getValue().asString());
            }
        }
        //生成密码
        System.out.println(BcryptPasswordUtil.createBCryptPassword("123456"));
    }
}