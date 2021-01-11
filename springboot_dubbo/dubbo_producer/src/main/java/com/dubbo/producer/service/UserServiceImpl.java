package com.dubbo.producer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.api.UserService;
import org.springframework.stereotype.Component;


@Service(interfaceClass = UserService.class) //dubbo 提供的
@Component
public class UserServiceImpl implements UserService {

    @Override
    public String findName() {
        return "张丹";
    }
}
