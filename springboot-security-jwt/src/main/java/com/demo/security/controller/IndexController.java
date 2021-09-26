package com.demo.security.controller;


import com.demo.security.domain.loginVo.UserLogin;
import com.demo.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author: lkz
 * @date:Created 2021/9/26
 */
@Controller
@RequestMapping("/login")
public class IndexController {

//    @Resource
//    private AuthenticationManager authenticationManager;


    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index() {
        return "login";
    }

//    @PostMapping("/userLogin")
//    public void userLogin(@RequestBody UserLogin vo){
//        System.out.println(vo);
//        Authentication authentication = null;
//        authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(vo.getUserName(), vo.getPassword()));
//
//    }

}
