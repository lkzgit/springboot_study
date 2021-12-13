package com.demo.user.aop;


import com.demo.user.aop.annotation.ArgsAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author lkz
 * @date 2021/12/13 9:57
 * @description  测试方法 参数匹配注解
 *
 */
@Aspect
@Component
@Slf4j
public class AopArgsDemo {

    @Pointcut("@annotation(com.demo.user.aop.annotation.ArgAnnotationMethod)")
    public void pointParam(){

    }

    @Before("pointParam()")
    public static void startArs(JoinPoint joinPoint) throws IllegalAccessException, NoSuchFieldException {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");

        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();//获取参数的名字
        final Object[] args = joinPoint.getArgs();// 获取参数的值
        String name = joinPoint.getSignature().getName();//获取方法的名字
        //获取参数注解 :1维是参数，2维是注解
        Annotation[][] annotations  = methodSignature.getMethod().getParameterAnnotations();
        for(int i=0;i<annotations.length;i++){
         Object param=args[i];
        Annotation[] paramAn= annotations[i];
        //参数为空 直接下一个参数
            if(param==null || paramAn.length==0){
                continue;
            }
            for(Annotation annotation:paramAn){
                //判断是否包含某个制定的注解
                if(annotation.annotationType().equals(ArgsAnnotation.class)){
                    //效验改参数 验证一次并退出改注解
                    Field field = String.class.getDeclaredField("value");
                    field.setAccessible(true);
                    field.set(param,field.get("南京"));
                    break;
                }
            }
        }

        System.out.println("AopArgsDemo切面执行前获取参数方法："+name+"方法开始执行，参数是："+ Arrays.asList(args));
    }

//    @Around("pointParam()")
//    public static void startArsAfter(ProceedingJoinPoint joinPoint) throws Throwable {
//
//
//        Object proceed = joinPoint.proceed();
//        final Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        final Object[] args = joinPoint.getArgs();// 获取参数的值
//        String name = joinPoint.getSignature().getName();//获取方法的名字
//        //获取参数注解 :1维是参数，2维是注解
//        Annotation[][] annotations  = methodSignature.getMethod().getParameterAnnotations();
//        for(int i=0;i<annotations.length;i++){
//            Object param=args[i];
//            Annotation[] paramAn= annotations[i];
//            //参数为空 直接下一个参数
//            if(param==null || paramAn.length==0){
//                continue;
//            }
//            for(Annotation annotation:paramAn){
//                //判断是否包含某个制定的注解
//                if(annotation.annotationType().equals(ArgsAnnotation.class)){
//                    //效验改参数 验证一次并退出改注解
//                    Field field = String.class.getDeclaredField("value");
//                    field.setAccessible(true);
//                    field.set(param,field.get("上海"));
//                    break;
//                }
//            }
//        }
//
//        System.out.println("AopArgsDemo切面执行之后获取参数方法："+name+"方法开始执行，参数是："+ Arrays.asList(args));
//    }



}
