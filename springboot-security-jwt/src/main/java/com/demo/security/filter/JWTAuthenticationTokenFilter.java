package com.demo.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.demo.security.domain.User;
import com.demo.security.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: JWT接口请求校验拦截器请求接口时会进入这里验证Token是否合法和过期
 * @Author: lkz
 * @Date: 2021/9/226
 **/
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {
    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        // 获取请求头中JWT的Token
        String token = request.getHeader("Authorization");
        if (null != token) {
            try {
                // 解析JWT
                Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
                // 获取用户名
                String username = null;
                String userId = null;
                String authority = null;
                for (Map.Entry<String, Claim> entry : stringClaimMap.entrySet()) {
                    if ("userId".equals(entry.getKey())) {
                        userId = entry.getValue().asString();
                    }
                    if ("account".equals(entry.getKey())) {
                        username = entry.getValue().asString();
                    }
                    if ("authorities".equals(entry.getKey())) {
                        authority = entry.getValue().asString();
                    }
                }
                if (!StringUtils.isBlank(username) && !StringUtils.isBlank(userId)) {
                    // 获取角色
                    Set<GrantedAuthority> authorities = new HashSet<>();
                    if (!StringUtils.isBlank(authority)) {
                        List<Map<String, String>> authorityMap = JSONObject.parseObject(authority, List.class);
                        authorityMap.forEach(
                                role -> authorities.add(new SimpleGrantedAuthority(role.get("authority")))
                        );
                    }
                    //组装参数
                    User user = new User();
                    user.setAccount(username);
                    user.setUserId(Integer.valueOf(userId));
                    user.setGrantedAuthorities(authorities);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, userId, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (TokenExpiredException e) {
                log.info("Token已经过期");
            } catch (SignatureVerificationException e) {
                log.info("Token无效");
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}
