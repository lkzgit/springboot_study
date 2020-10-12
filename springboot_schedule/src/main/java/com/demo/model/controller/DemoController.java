package com.demo.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class DemoController {

    @Autowired
    private  TestAsync testAsync;

    @GetMapping("test")
    public String test(){
        return "定时任务";
    }

    @GetMapping("testAsync")
    public String exeTask() throws InterruptedException {
        long begin=System.currentTimeMillis();
        //同步任务 类似一个线程池
//        testAsync.test1();
//        testAsync.test2();
//        testAsync.test3();
        //获取异步任务结果
        Future<String> test4=testAsync.test4();
        Future<String> test5=testAsync.test5();
        Future<String> test6=testAsync.test6();
        for(;;){
            if(test4.isDone()&&test5.isDone()&&test6.isDone()){
                break;
            }
        }
        long end=System.currentTimeMillis();

        System.out.println("总耗时："+(end-begin));
        return "异步成功"+(end-begin);
    }
}
