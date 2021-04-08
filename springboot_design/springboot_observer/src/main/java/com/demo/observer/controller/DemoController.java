package com.demo.observer.controller;

import com.demo.observer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册》发送邮件》发送优惠券
     * @param username
     * @return
     */
    @GetMapping("/register")
    public String register(String username) {
        userService.register(username);
        return "success";
    }

}
