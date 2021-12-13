package com.demo.user.aop;//package com.demo.user.aop;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
///**
// * @author lkz
// * @date 2021/12/13 9:57  https://www.cnblogs.com/liantdev/p/10125284.html 参数配置
// * @description  基础配置学习
// *
// *  @Before:在目标方法之前运行：前置通知
// *  @After:在目标方法之后运行：后置通知
// *  @AfterReturning:在目标方法正常返回之后：返回通知
// *  @AfterThrowing:在目标方法抛出异常后开始运行：异常通知
// *  @Around:环绕：环绕通知
// *
// * 1 所有公有方法的执行
// *  execution(public * *(..))
// * 2 所有以set开头的公有方法的执行
// *  execution(* set*(..))
// * 3 AccountService接口下的所有方法的执行
// *  execution(* com.xyz.service.AccountService.*(..))
// * 4 com.xyz.service包下的所有方法的执行
// *  execution(* com.xyz.service.*.*(..))
// * 5 com.xyz.service包及其子包下的所有方法的执行
// *  execution(* com.xyz.service..*.*(..))
// * 6 匹配com.xyz.service包下的所有类的所有方法（不含子包）
// *  within(com.xyz.service.*)
// * 7 com.xyz.service包和子包的所有方法
// *  within(com.xyz.service..*)
// *
// * @Retention：注解的保留位置　　　　　　　　　
// * 　　　@Retention(RetentionPolicy.SOURCE) //注解仅存在于源码中，在class字节码文件中不包含
// * 　　　@Retention(RetentionPolicy.CLASS) // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
// * 　　　@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
// *
// * @Target:注解的作用目标
// * 　　　@Target(ElementType.TYPE) //接口、类、枚举、注解
// * 　　　@Target(ElementType.FIELD) //字段、枚举的常量
// * 　　　@Target(ElementType.METHOD) //方法
// * 　　　@Target(ElementType.PARAMETER) //方法参数
// * 　　　@Target(ElementType.CONSTRUCTOR) //构造函数
// * 　　　@Target(ElementType.LOCAL_VARIABLE)//局部变量
// * 　　　@Target(ElementType.ANNOTATION_TYPE)//注解
// * 　　　@Target(ElementType.PACKAGE) ///包
// *
// * @Document：说明该注解将被包含在javadoc中
// * @Inherited：说明子类可以继承父类中的该注解
// *
// */
//@Aspect
//@Component
//@Slf4j
//public class AopDemo1 {
//
//    //全局扫描注解
//    @Pointcut("execution(* com.demo.user.controller.*.*(..))")
//    public void point(){
//
//    }
//
//    @Before("point()")
//    public static void start(JoinPoint joinPoint){
//        Object[] args = joinPoint.getArgs();
//        String name = joinPoint.getSignature().getName();
//        System.out.println("AopDemo1切面执行："+name+"方法开始执行，参数是："+ Arrays.asList(args));
//    }
//
//    @AfterReturning("point()")
//    public static void stop(JoinPoint joinPoint){
//        String name = joinPoint.getSignature().getName();
//        System.out.println("AopDemo1切面执行："+name+"方法执行完成，结果是：");
//
//    }
//
//    @AfterThrowing("point()")
//    public static void logException(JoinPoint joinPoint){
//        String name = joinPoint.getSignature().getName();
//        System.out.println("AopDemo1切面执行："+name+"方法出现异常：");
//    }
//
//    @After("point()")
//    public static void end(JoinPoint joinPoint){
//        String name = joinPoint.getSignature().getName();
//        System.out.println("AopDemo1切面执行："+name+"方法执行结束了......");
//    }
//
//    @Around("point()")
//    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
//    {
//        Object retValue = null;
//        System.out.println("AopDemo1切面执行环绕通知之前");
//        retValue = proceedingJoinPoint.proceed();
//        System.out.println("AopDemo1切面执行环绕通知之后");
//        return retValue;
//    }
//
//}
