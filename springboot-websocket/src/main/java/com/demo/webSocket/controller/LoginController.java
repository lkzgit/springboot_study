package com.demo.webSocket.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.demo.webSocket.server.WebSocketServer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lkz
 * @date 2021/9/16 18:58
 * @description
 */
@Controller
@CrossOrigin
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
            QrConfig qrConfig=new QrConfig();
            qrConfig.setHeight(200);
            qrConfig.setWidth(200);
            String content="localhost:8011/toOk?uuId="+uuid;
            QrCodeUtil.generate(content, 200, 200, "jpg",response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @GetMapping("toOk")
    public String toOk(@RequestParam(value = "uuId",required = false)String uuId){
        System.out.println("toOk:"+uuId);
        return "ok";
    }

    @GetMapping("saoMiaoCaodeImg")
    public ModelAndView saoMiaoCaodeImg(@RequestParam(value = "uuId",required = false)String uuId){
        System.out.println("扫描成功之后:"+uuId);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("success");
        modelAndView.getModel().put("uuid",uuId);
        return modelAndView;
    }
    @GetMapping("getUUID")
    @ResponseBody
    public String getUUID(String uuid){
        String s = loginThreadLocal.get(uuid);
        System.out.println("获取uuid:"+s);
        return s;
    }


    @GetMapping("index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("请求成功");
    }

    @GetMapping("page")
    public ModelAndView page(){
        return new ModelAndView("websocket");
    }

    @RequestMapping("/push/{toUserId}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message,toUserId);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    //动力节点
    private AtomicInteger idProducer = new AtomicInteger();

    @RequestMapping("/donglichat")
    public String donglichat(Model model) {
        model.addAttribute("username","user" + idProducer.getAndIncrement());
        return "donglijiedian";
    }



}
