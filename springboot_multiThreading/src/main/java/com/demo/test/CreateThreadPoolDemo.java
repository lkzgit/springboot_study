package com.demo.test;

import com.demo.mangyThread.RunThread;

import java.util.concurrent.*;

/**
 * @author lkz
 * @date 2021/11/12 14:05
 * @description
 * ThreadPoolExecutor 线 程 池 调 度 器 为 每 个 任 务 执 行 前 后 ， 都 提 供 了 钩 子 方 法 。
 * ThreadPoolExecutor 类提供了三个钩子方法（空方法），这三个空方法一般用作被子类重写，具体
 * 如下：
 * //任务执行之前的钩子方法（前钩子）
 * protected void beforeExecute(Thread t, Runnable r) { }
 * //任务执行之后的钩子方法（后钩子）
 * protected void afterExecute(Runnable r, Throwable t) { }
 * //线程池终止时的钩子方法（停止钩子）
 * protected void terminated() { }
 *
 */
public class CreateThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = new ThreadPoolExecutor(2, //coreSize
                4, //最大线程数
                60,//空闲保活时长
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2)) //等待队列
        {
            //继承：调度器终止钩子
            @Override
            protected void terminated()
            {
                System.out.println("调度器已经终止!");
            }
            //继承：执行前钩子
            @Override
            protected void beforeExecute(Thread t, Runnable target)
            {
                System.out.println( target +"前钩被执行");
                //记录开始执行时间
                super.beforeExecute(t, target);
            }
            //继承：执行后钩子
            @Override
            protected void afterExecute(Runnable target, Throwable t)
            {
                super.afterExecute(target, t);
                System.out.println(target+"后执行");
            }
        };
        for (int i = 1; i <= 5; i++)
        {
            pool.execute(new RunThread());
        }
        //等待 10 秒
        Thread.sleep(10000);
        System.out.println("关闭线程池");
        pool.shutdown();

    }

}
