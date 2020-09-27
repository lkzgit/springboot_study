package com.demo.model.security;

import com.demo.model.model.User;
import com.demo.model.service.UserService;
import com.demo.model.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 定义实现security 提供的
 */
@Service
public class MyUserDetailmpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User sysUser=userService.findByUsername(username);
        if(sysUser==null){ //若用户名不对，直接返回null，表示认证失败。
             return null; }

        Set<String> permissions = userService.findPermissions(username);
        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
//       return new JwtUserDetails(username, sysUser.getPassword(), grantedAuthorities);
//        UserDetails userDetails = org.springframework.security.core.userdetails.
//                User.withUsername(sysUser.getUsername()).password(sysUser.getPassword()).authorities(grantedAuthorities).build();
//        return userDetails;
        return new LoginUser(sysUser,permissions);
    }
}
