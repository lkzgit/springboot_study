package com.demo.user.controller;

import com.demo.user.aop.annotation.ArgAnnotationMethod;
import com.demo.user.aop.annotation.ArgsAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lkz
 * @date 2021/12/13 10:09
 * @description
 */
@RestController
@Slf4j
@RequestMapping("aop")
public class AopTestController {

    @GetMapping("test1")
    public void test1(){

        System.out.println("aop test--方法执行---");
    }

    @GetMapping("test2")
    public void testException() throws Exception {
        int a=10/0;
        throw new Exception();
    }
    @GetMapping("test3")
    public void testArg(String name){
        System.out.println("aop 测试方法路径参数："+name);
    }

    @GetMapping("test4")
    @ArgAnnotationMethod
    public String testArg4(@ArgsAnnotation String name,@ArgsAnnotation String age,String adree){
        System.out.println("aop 测试方法路径参数："+name+"-----年龄"+age+"----地址:"+adree);

        return name+"---"+age+"--"+adree;
    }
}
