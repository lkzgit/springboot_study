package com.demo.nien;

import java.util.concurrent.TimeUnit;

/**
 * @author lkz
 * @date 2021/11/11 17:14
 * @description
 * 如果线程被 Object.wait, Thread.join 和 Thread.sleep 三种方法之一阻塞，此
 * 时调用该线程的 interrupt()方法，那么该线程将抛出一个 InterruptedException 中断异常（该线程
 * 必须事先预备好处理此异常），从而提早地终结被阻塞状态。
 */
public class InterruptDemo {
    public static final int SLEEP_GAP = 5000;//睡眠时长
    public static final int MAX_TURN = 50;//睡眠次数
    static class SleepThread extends Thread
    {
        static int threadSeqNumber = 1;
        public SleepThread()
        {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
        }
        public void run()
        {
            try
            {
                System.out.println(getName() + " 进入睡眠.");
                // 线程睡眠一会
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
                System.out.println(getName() + " 发生被异常打断.");
                return;
            }
            System.out.println(getName() + " 运行结束.");
        }
    }
    public static void main(String args[]) throws InterruptedException
    {
        Thread thread1 = new SleepThread();
        thread1.start();
        Thread thread2 = new SleepThread();
        thread2.start();
        Thread.sleep(2000);//主线程等待 2 秒
        thread1.interrupt(); //打断线程 1
        Thread.sleep(5000);//主线程等待 5 秒
        thread2.interrupt(); //打断线程 2，此时线程 2 已经终止
        Thread.sleep(1000);//主线程等待 1 秒
        System.out.println("程序运行结束.");
    }





}
