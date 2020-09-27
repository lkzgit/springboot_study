package com.demo.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * security提供的用户模型
 *      boolean enabled 是否可用
 *      boolean accountNonExpired 账户是否失效
 *      boolean credentialsNonExpired 秘密是否失效
 *      boolean accountNonLocked 账户是否锁定
 *
 */
public class JwtUserDetails extends User  {
    public JwtUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public JwtUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    private Set<? extends GrantedAuthority> authorities;




}
