package com.demo.aop.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author lkz
 * @date 2021/11/30 14:27
 * @description
 */
@Aspect
@Component
@Slf4j
public class AopLog2 {

    @Pointcut("execution(public * com.demo.aop.controller.*Controller.*(..))")
    public void annotationPointCut() {

    }

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object responseObj = null;
        System.out.println("AopLog2:"+joinPoint);
        return responseObj;
    }

}
