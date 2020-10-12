package com.demo.model.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 异步任务类
 */
@Component
@Async
public class TestAsync {

    /**
     * @Async 标识这是一个异步的方法， 类
     * @throws InterruptedException
     */
   // @Async
    public void test1() throws InterruptedException {
        long time=System.currentTimeMillis();

        Thread.sleep(1000L);//睡眠1秒
        long end=System.currentTimeMillis();

        System.out.println("任务一耗时:"+(end-time));
    }
  //  @Async
    public void test2() throws InterruptedException {
        long time=System.currentTimeMillis();

        Thread.sleep(2000L);//睡眠1秒
        long end=System.currentTimeMillis();
        System.out.println("任务2耗时:"+(end-time));
    }
   // @Async
    public void test3() throws InterruptedException {
        long time=System.currentTimeMillis();

        Thread.sleep(3000L);//睡眠1秒
        long end=System.currentTimeMillis();
        System.out.println("任务3耗时:"+(end-time));
    }
    //获取异步结果

    public Future<String> test4() throws InterruptedException {
        long time=System.currentTimeMillis();

        Thread.sleep(3000L);
        long end=System.currentTimeMillis();
        System.out.println("任务4耗时:"+(end-time));
        return new AsyncResult<String>("任务4");
    }
    public Future<String> test5() throws InterruptedException {
        long time=System.currentTimeMillis();

        Thread.sleep(2000L);
        long end=System.currentTimeMillis();
        System.out.println("任务5耗时:"+(end-time));
        return new AsyncResult<String>("任务5");
    }

    public Future<String> test6() throws InterruptedException {
        long time=System.currentTimeMillis();

        Thread.sleep(1000L);
        long end=System.currentTimeMillis();
        System.out.println("任务6耗时:"+(end-time));
        return new AsyncResult<String>("任务6");
    }

}
