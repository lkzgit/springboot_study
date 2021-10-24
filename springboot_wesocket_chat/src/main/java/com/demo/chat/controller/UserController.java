package com.demo.chat.controller;


import com.demo.chat.pojo.Result;
import com.demo.chat.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @version v1.0
 * @ClassName: UserController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
@RestController
public class UserController {

    @RequestMapping("/login")
    public Result login(User user, HttpSession session) {
        Result result = new Result();
        if(user != null && "123".equals(user.getPassword())) {
            result.setFlag(true);
            //将数据存储到session对象中
            session.setAttribute("user",user.getUsername());
        } else {
            result.setFlag(false);
            result.setMessage("登陆失败");
        }
        return result;
    }

    @RequestMapping("/getUsername")
    public String getUsername(HttpSession session) {
        String username = (String) session.getAttribute("user");
        return username;
    }
}
