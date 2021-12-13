package com.demo.user.aop.annotation;

import java.lang.annotation.*;

/**
 * @author lkz
 * @date 2021/12/13 14:43
 * @description 参数方法
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArgAnnotationMethod {
}
