package com.demo.security.filter;

import com.demo.security.exception.VerificationCodeException;
import com.demo.security.hander.UserLoginFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 图形验证码拦截器
 *  author : lkz
 */
public class VerificationCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler failureHandler=new UserLoginFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 非登录请求不验证验证码
        if(!"/login".equals(request.getRequestURI())){
            filterChain.doFilter(request,response);
        }else{
            try {
                String code = request.getParameter("code");
                HttpSession session = request.getSession();
                String saveCode = (String)session.getAttribute("code");
                saveCode="456";
                if(!StringUtils.isEmpty(saveCode)){
                    //随手清楚验证码，无论成功还是失败 客户端在登录时刷新验证码
                    session.removeAttribute("code");
                }
                // 校验不通过 抛出异常
                if(StringUtils.isEmpty(code)|| StringUtils.isEmpty(saveCode)|| !code.equals(saveCode)){
                    throw new VerificationCodeException();
                }
                filterChain.doFilter(request,response);
            }catch (VerificationCodeException e){
                failureHandler.onAuthenticationFailure(request,response,e);
            }

        }
    }
}
