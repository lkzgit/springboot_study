package com.boot.guigu;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in Callable");
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }
}

 class CallableDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {


        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B").start();//多个线程执行 一个FutureTask的时候，只会计算一次

        // 输出FutureTask的返回值
        System.out.println("result FutureTask " + futureTask.get());
    }

}

