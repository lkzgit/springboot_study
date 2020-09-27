package com.boot.demo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 表示拦截api请求下的所有
 * 自定义的filter拦截器要交给容器管理
 * 在启动器上@ServletComponentScan 或者@Component
 * @WebFilter设置 规则 被spring扫描
 */
//@WebFilter(urlPatterns = "/api/*", filterName = "loginFilter")
//@Component
public class LoginFilter implements Filter {
    /**
     * 初始化 容器加载的时候调用
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("拦截器初始化----");
    }

    /**
     * 逻辑操作 请求拦截
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String username = req.getParameter("username");

        if ("user".equals(username)) {
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            resp.sendRedirect("/index.html");
            return;
        }

    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {
        System.out.println("拦截器销毁-----");
    }
}
