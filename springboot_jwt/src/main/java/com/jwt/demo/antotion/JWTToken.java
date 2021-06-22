package com.jwt.demo.antotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lkz
 * @create
 * @desc
加上该注解的接口需要登录才能访问
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JWTToken {
    boolean required() default true;
}
