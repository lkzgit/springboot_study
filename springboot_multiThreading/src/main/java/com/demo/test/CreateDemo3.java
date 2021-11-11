package com.demo.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author lkz
 * @date 2021/11/11 15:28
 * @description
 * 使用 Callable 和 FutureTask 创建线程的具体步骤
 * （1）创建一个 Callable 接口的实现类，并实现其 call()方法，编写好异步执行的具体逻辑，
 * 并且可以有返回值。
 * （2）使用 Callable 实现类的实例，构造一个 FutureTask 实例。
 * （3）使用 FutureTask 实例，作为 Thread 构造器的 target 入参，构造新的 Thread 线程实例；
 * （4）调用 Thread 实例的 start 方法启动新线程，启动新线程的 run()方法并发执行。其内部的
 * 执行过程为：启动 Thread 实例的 run()方法并发执行后，会执行 FutureTask 实例的 run()方法，最
 * 终会并发执 Callable 实现类的 call()方法。
 * （5）调用 FutureTask 对象的 get()方法，阻塞性的获得并发线程的执行结果。
 */
public class CreateDemo3 {


    public static final int MAX_TURN = 5;
    public static final int COMPUTE_TIMES = 100000000;
    //①创建一个 Callable 接口的实现类
    static class ReturnableTask implements Callable<Long> {
        //②编写好异步执行的具体逻辑，并且可以有返回值
        public Long call() throws Exception{
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 线程运行开始.");
            Thread.sleep(1000);
            for (int i = 0; i < COMPUTE_TIMES; i++) {
                int j = i * 10000;
            }
            long used = System.currentTimeMillis() - startTime;
            System.out.println(Thread.currentThread().getName() + " 线程运行结束.");
            return used;
        }
    }
    public static void main(String args[]) throws InterruptedException, ExecutionException {
        ReturnableTask task=new ReturnableTask();//③
        FutureTask<Long> futureTask = new FutureTask<Long>(task);//④

        Thread thread = new Thread(futureTask, "returnableThread");//⑤
        thread.start();//⑥
        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " 让子弹飞一会儿.");
        System.out.println(Thread.currentThread().getName() + " 做一点自己的事情.");
        for (int i = 0; i < COMPUTE_TIMES / 2; i++) {
            int j = i * 10000;
        }
        System.out.println(Thread.currentThread().getName() + " 获取并发任务的执行结果.");
        try {
            System.out.println(thread.getName()+"线程占用时间："
                    + futureTask.get());//⑦
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+ " 运行结束.");
    }

}
