package com.demo.consumer.controller;

import com.demo.consumer.annotation.NeedDecryptMethod;
import com.demo.consumer.annotation.NeedEncryptMethod;
import com.demo.consumer.entity.User;
import com.demo.consumer.mapper.UserMapper;
import com.demo.consumer.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2021年12月08日 22:16:00
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @NeedDecryptMethod
    @GetMapping("getList")
    public BaseResult getList(){
        List<User> list = userMapper.getList();
        return BaseResult.ok(list);

    }
    @NeedEncryptMethod
    @PostMapping("add")
    public String add(@RequestBody User user){
      int i=  userMapper.insert(user);
      return "ok";
    }
}
