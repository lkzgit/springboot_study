package com.boot.demo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springboot 旧版本2.0之前的版本
 */
//@Configuration
public class CustomOldWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        System.out.println("2.0之前的拦截器");
        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/api/*");// 拦截api/请求

        super.addInterceptors(registry);
    }
}
