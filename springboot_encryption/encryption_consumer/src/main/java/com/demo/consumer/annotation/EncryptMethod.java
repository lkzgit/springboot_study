package com.demo.consumer.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName EncryptMethod.java
 * @Description 定义方法注解 https://www.jianshu.com/p/749259832204
 * @createTime 2021年12月09日 00:26:00
 *  *  安全字段注解
 *  * 加在需要加密/解密的方法上
 *  * 实现自动加密解密
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface EncryptMethod {
}
