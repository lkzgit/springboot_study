package com.jwt.demo.until;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**")//拦截所有请求
                .excludePathPatterns("/user/login");//放行登录请求
    }


//    /**
//     *  拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authenticationInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/**","/BackRoleMenu/getRoleMenuMany",
//                        "/swagger-resources/**",
//                        "/swagger-ui.html/**","/doc.html","/webjars/**");//放行登录
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //配置拦截器访问静态资源
//        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }
//
//
//
//    @Bean
//    public AuthenticationInterceptor authenticationInterceptor() {
//        return new AuthenticationInterceptor();
//    }

}
