package com.demo.security.service.impl;


import com.demo.security.domain.Permission;
import com.demo.security.domain.Role;
import com.demo.security.domain.User;
import com.demo.security.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**

 * @version :
 * @author: lkz
 */
@Service
public class CustomUserServiceImpl implements AuthenticationProvider {
    /**
     * 可以通过构造方法注入对象
     */
    final UserMapper userMapper;

    public CustomUserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        //获取用户信息
        User user = userMapper.findByUserName(userName);
        //创建GrantedAuthority集合
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (null == user) {
            //仍需要细化处理
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (user.getState() != 0) {
            throw new LockedException("该用户已被冻结");
        }
        //获取用户的角色
        List<Role> roleList = user.getRoleList();
        roleList.forEach(role -> {
            /*
             构建所有权限集合==ROLE_角色+权限
             hasRole([role])	用户拥有指定的角色时返回true(hasRole()默认会将配置中的 role 带有 ROLE_ 前缀再和用户的角色权限 进行对比)
             hasAnyRole([role1,role2])	用户拥有任意一个指定中的角色时返回true
             hasAuthority([auth])	同hasRole()但不添加前缀 ROLE_
             hasAnyAuthority([auth1,auth2])	同hasAnyRole([auth1,auth2])，但不添加前缀 ROLE_
             */
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
            //获取权限
            List<Permission> permissionList = role.getPermissionList();
            //添加角色到grantedAuthorities集合中
            permissionList.forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermissionValue())));
        });
        /*如果需要带当前user对象过去，那必须在user的实体类加上 Set<GrantedAuthority> grantedAuthorities
          而且还要实现UserDetails
          然后把权限设置过去，如果不需要当前对象可以使用security里面的user对象
          new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities)
         */
        user.setGrantedAuthorities(grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(user, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
