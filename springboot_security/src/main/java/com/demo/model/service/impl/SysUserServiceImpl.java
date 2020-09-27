package com.demo.model.service.impl;

import java.util.HashSet;
import java.util.Set;

import com.demo.model.model.User;
import com.demo.model.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class SysUserServiceImpl implements UserService {

    @Override
    public User findByUsername(String username) {
        User user = new User();
        user.setId(1);
        user.setUsername(username);
        String password = new BCryptPasswordEncoder().encode("123");
        user.setPassword(password);
        return user;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("sys:user:view");
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        permissions.add("sys:user:delete");
        return permissions;
    }

}
