package com.demo.consumer.interceptor;//package com.demo.consumer.interceptor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
///**
// * @author lkz
// * @date 2021/12/6 18:30
// * @description
// */
////@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    @Autowired(required = false)
//    AskTokenInterceptor askTokenInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(askTokenInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/getToken","/generateToken","/checkToken","/con/test2");;
//
//    }
//}
