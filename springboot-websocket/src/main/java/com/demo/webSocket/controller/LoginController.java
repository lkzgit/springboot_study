package com.demo.webSocket.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lkz
 * @date 2021/9/16 18:58
 * @description
 */
@Controller
public class LoginController {

    private static final Map<String,String> loginThreadLocal = new ConcurrentHashMap<>();

    @GetMapping("toIndex")
    public String toIndex(){
        return "index";
    }

    @GetMapping("createImg")
    @ResponseBody
    public void createImg(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");

        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try {
            //这里没啥操作 就是生成一个UUID插入 数据库的表里
            String uuid = UUID.randomUUID().toString();
            System.out.println("生成的UUID:"+uuid);
            loginThreadLocal.put(uuid,uuid);
            response.setHeader("uuid", uuid);
            // 这里是开源工具类 hutool里的QrCodeUtil
            // 网址：http://hutool.mydoc.io/
            QrCodeUtil.generate(uuid, 200, 200, "jpg",response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @GetMapping("getUUID")
    @ResponseBody
    public String getUUID(String uuid){
        String s = loginThreadLocal.get(uuid);
        System.out.println("获取uuid:"+s);
        return s;
    }


}
