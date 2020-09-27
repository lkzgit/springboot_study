package com.demo.model.filter;

import com.alibaba.fastjson.JSON;

import com.demo.model.SecurityUtils;
import com.demo.model.exception.CustomException;

import com.demo.model.security.GrantedAuthorityImpl;
import com.demo.model.service.TokenService;
import com.demo.model.service.UserService;
import com.demo.model.untils.StringUtils;
import com.demo.model.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException,CustomException {
        System.out.println("进入拦截器");

        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            Authentication authentication = SecurityUtils.getAuthentication();

                Set<String> permissions = userService.findPermissions(loginUser.getUsername());
                List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
                tokenService.verifyToken(loginUser);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, grantedAuthorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }

        chain.doFilter(request, response);
    }


    /**
     * 无需转发，直接返回Response信息
     *  this.response401(response,"JwtTOken登录已失效请重新登录");
     */
    private void response401(HttpServletResponse response, String msg) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (Writer out= new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        ) {
            String data = JSON.toJSONString(msg);
            out.append(data);
        } catch (IOException e) {
            logger.error("直接返回Response信息出现IOException异常:{}",e);
            throw new CustomException("直接返回Response信息出现IOException异常:" + e.getMessage());
        }
    }
}
