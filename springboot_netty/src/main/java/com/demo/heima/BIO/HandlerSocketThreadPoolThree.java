package com.demo.heima.BIO;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程处理类
 */
public class HandlerSocketThreadPoolThree {

    // 创建一个线程池
    private ExecutorService executor;

    public HandlerSocketThreadPoolThree(int maxPool,int queue){
        executor= new ThreadPoolExecutor(3,maxPool,120L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queue));

    }

    public void execute(Runnable  task){
        this.executor.execute(task);
    }

}
