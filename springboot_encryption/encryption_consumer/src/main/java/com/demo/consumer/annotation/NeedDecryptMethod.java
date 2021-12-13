package com.demo.consumer.annotation;

import java.lang.annotation.*;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName NeedDecryptMethod.java
 * @Description 需要解密的方法注解
 * @createTime 2021年12月12日 00:35:00
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedDecryptMethod {
}
