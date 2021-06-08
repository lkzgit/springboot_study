package com.demo.mangyThread;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * 实现Callable接口通过FutureTask包装器来创建Thread线程
 */
public class CallThread implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println("callable");
        return null;
    }


    public static <T> void main(String[] args) {

        Callable callThread = new CallThread();
        //由Callable<Integer>创建一个FutureTask<Integer>对象：
        FutureTask<T> oneTask = new FutureTask<T>(callThread);
        //注释：FutureTask<Integer>是一个包装器，它通过接受Callable<Integer>来创建，它同时实现了Future和Runnable接口。
        //由FutureTask<Integer>创建一个Thread对象：
        Thread oneThread = new Thread(oneTask);
        oneThread.start();
        //至此，一个线程就创建完成了

    }
}
