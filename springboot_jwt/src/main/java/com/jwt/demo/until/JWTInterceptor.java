package com.jwt.demo.until;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String,String> map=new HashMap<>();
        String token = request.getHeader("token");
        try{
            JWTUntil.verify(token);
            map.put("msg","验证成功");
           return true;
        }catch(SignatureVerificationException e){
            e.getStackTrace();
            map.put("msg","无效签名");
        }catch (TokenExpiredException e){
            e.getStackTrace();
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException e){
            e.getStackTrace();
            map.put("msg","token算法不一致");
        }
        catch (Exception e){
            e.getStackTrace();
            map.put("msg","token无效");
        }
        //将map 转为json jackon
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json:charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
