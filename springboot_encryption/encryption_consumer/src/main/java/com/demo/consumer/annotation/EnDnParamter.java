package com.demo.consumer.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName EnDnParamter.java
 * @Description https://www.jianshu.com/p/760b284fc269
 * @createTime 2021年12月08日 23:07:00
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface EnDnParamter {

    /**
     * 入参是否解密，默认true解密
     */
    boolean inDecode() default true;

    /**
     * 出参是否加密，默认true加密
     */
    boolean outEncode() default true;

}
