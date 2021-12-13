package com.demo.user.aop.annotation;

import java.lang.annotation.*;

/**
 * @author lkz
 * @date 2021/12/13 10:26
 * @description
 */
@Documented
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArgsAnnotation {

}
