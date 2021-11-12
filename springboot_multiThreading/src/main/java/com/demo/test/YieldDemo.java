package com.demo.test;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lkz
 * @date 2021/11/11 20:59
 * @description
 *      线程的 yield（让步）操作的作用：是让目前正在执行的线程放弃当前的执行，让出 CPU 的
 * 执行权限，使得 CPU 去执行其他的线程。处于让步状态的 JVM 层面的线程状态，仍然是
 * RUNNABLE 可执行状态；但是，该线程所对应的操作系统层面的线程，在状态上来说，会从执
 * 行状态变成就绪状态。线程在 yield 时，线程放弃和重占 CPU 的时间是不确定的，可能是刚刚放
 * 弃 CPU，马上又获得 CPU 执行权限，重新开始执行
 */
public class YieldDemo {

    public static final int MAX_TURN = 20;//执行次数

    public static AtomicInteger index = new AtomicInteger(0);//执行编号
    // 记录线程的执行次数
    private static Map<String, AtomicInteger> metric = new HashMap<>();
    //输出线程的执行次数
    private static void printMetric()
    {
        System.out.println("metric = " + metric);
    }
    static class YieldThread extends Thread
    {
        static int threadSeqNumber = 1;
        public YieldThread()
        {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
            //将线程加入到执行次数统计 map
            metric.put(this.getName(), new AtomicInteger(0));
        }
        public void run()
        {
            for (int i = 1; i < MAX_TURN && index.get() < MAX_TURN; i++)
            {
                System.out.println("线程优先级：" + getPriority());
                index.incrementAndGet();
                //统计一次
                metric.get(this.getName()).incrementAndGet();
                if (i % 2 == 0)
                {
                    //让步：出让执行的权限
                    Thread.yield();
                }
            }
            //输出所有线程的执行次数
            printMetric();
            System.out.println(getName() + " 运行结束.");
        }
    }
    @Test
    public void test() throws InterruptedException {
        Thread thread1 = new YieldThread();
        //设置为最高的优先级
        thread1.setPriority(Thread.MAX_PRIORITY);
        Thread thread2 = new YieldThread();
        //设置为最低的优先级
        thread2.setPriority(Thread.MIN_PRIORITY);
        System.out.println("启动线程.");
        thread1.start();
        thread2.start();
        Thread.sleep(3000);
    }

}
