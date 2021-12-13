package com.demo.consumer.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName EncryptField.java
 * @Description * 安全字段注解 https://www.jianshu.com/p/749259832204
 *               加在需要加密/解密的字段上
 * @createTime 2021年12月09日 00:27:00
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface EncryptField {
}
