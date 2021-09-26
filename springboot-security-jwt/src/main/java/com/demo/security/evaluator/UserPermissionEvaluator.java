package com.demo.security.evaluator;


import com.demo.security.domain.User;
import com.demo.security.service.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description: 自定义hasPermission权限注解验证
 * @Author: lkz
 * @Date: 2021/9/26
 **/

@Component
public class UserPermissionEvaluator implements PermissionEvaluator {


    @Resource
    UserService userService;

    public UserPermissionEvaluator() {
    }

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的permission
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     *
     * @param authentication 用户身份
     * @param targetUrl      请求路径
     * @param permission     请求路径权限
     * @return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        System.out.println(authentication.getPrincipal());
        // 获取用户信息
        User user = (User) authentication.getPrincipal();
        // 获取用户对应角色的权限(因为SQL中已经GROUP BY了，所以返回的list是不重复的)
        List<String> rolePermissions = new ArrayList<>();
        //获取权限
        Set<GrantedAuthority> grantedAuthorities = user.getGrantedAuthorities();
        grantedAuthorities.forEach(grantedAuthority -> rolePermissions.add(grantedAuthority.getAuthority()));
        // 权限对比
        return rolePermissions.contains(permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
