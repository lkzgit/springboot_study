package com.demo.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.demo.security.domain.User;
import com.demo.security.mapper.UserMapper;
import com.demo.security.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**

 * @author: lkz
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}
