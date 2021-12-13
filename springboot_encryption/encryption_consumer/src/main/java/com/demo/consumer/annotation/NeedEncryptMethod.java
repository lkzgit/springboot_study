package com.demo.consumer.annotation;

import java.lang.annotation.*;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName NeedEncryptMethod.java
 * @Description 需要加密的方法注解
 * @createTime 2021年12月12日 00:06:00
 */

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedEncryptMethod {
}
