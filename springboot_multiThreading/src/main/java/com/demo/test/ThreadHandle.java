package com.demo.test;

import com.demo.mangyThread.RunThread;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lkz
 * @date 2021/11/12 14:43
 * @description 自定义线程拒绝策略
 */
public class ThreadHandle {

    //一个简单的线程工厂
    static public class SimpleThreadFactory implements ThreadFactory
    {
        static AtomicInteger threadNo = new AtomicInteger(1);
        //实现其唯一的创建线程方法
        @Override
        public Thread newThread(Runnable target)
        {
            String threadName = "simpleThread-" + threadNo.get();
            System.out.println("创建一条线程，名称为：" + threadName);
            threadNo.incrementAndGet();
            //设置线程名称，和异步执行目标
            Thread thread = new Thread(target,threadName);
            //设置为守护线程
            thread.setDaemon(true);
            return thread;
        }
    }
    //自定义拒绝策略
    public static class CustomIgnorePolicy implements RejectedExecutionHandler
    {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e)
        {
            // 可做日志记录等
            System.out.println("自定义拒绝策略执行");
            System.out.println(r + " rejected; " +
                    " - getTaskCount: " + e.getTaskCount());

        }
    }


    public static void main(String[] args) throws InterruptedException {
        int corePoolSize = 2; //核心线程数
        int maximumPoolSize = 4; //最大线程数
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        //最大排队任务数
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        //线程工厂
        ThreadFactory threadFactory = new SimpleThreadFactory();
        //拒绝和异常处理策略
        RejectedExecutionHandler policy = new CustomIgnorePolicy();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime, unit,
                workQueue,
                threadFactory,
                policy);
        // 预启动所有核心线程
        pool.prestartAllCoreThreads();
        for (int i = 1; i <= 10; i++)
        {
            pool.execute(new RunThread());
        }
        //等待 10 秒
        Thread.sleep(5000);
        System.out.println("关闭线程池");
        pool.shutdown();



    }


}
