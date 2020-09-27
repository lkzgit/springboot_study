package com.boot.demo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器2.0之后
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        System.out.println("2.0之后");
        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/api/*/**");
       // registry.addInterceptor(new TwoIntercepter()).addPathPatterns("/api2/*/**");

        //.excludePathPatterns("/api2/xxx/**"); //拦截全部 /*/*/**

        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
