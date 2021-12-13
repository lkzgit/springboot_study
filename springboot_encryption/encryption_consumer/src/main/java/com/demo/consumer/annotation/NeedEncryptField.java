package com.demo.consumer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName NeedEncryptField.java
 * @Description 需要加密的字段
 * @createTime 2021年12月12日 00:08:00
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedEncryptField {


    String[] value() default "";
}
