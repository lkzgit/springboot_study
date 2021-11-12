package com.demo.test;

/**
 * @author lkz
 * @date 2021/11/11 20:46
 * @description
 * 1. 线程的 join 操作的三个版本
 * Join()方法是 Thread 类的一个实例方法，有三个重载版本：
 * //重载版本 1：此方法会把当前线程变为 TIMED_WAITING，直到被合并线程执行结束。
 * public final void join() throws InterruptedException：
 * //重载版本 2：此方法会把当前线程变为 TIMED_WAITING，直到被合并线程结束，或者等待被合并
 * 线程执行 millis 的时间。
 * public final synchronized void join(long millis) throws
 * InterruptedException：
 * //重载版本 2：此方法会把当前线程变为 TIMED_WAITING，直到被合并线程结束，或者等待被合并
 * 线程执行 millis+nanos 的时间。
 * public final synchroinzed void join(long millis, int nanos) throws
 * InterruptedException：
 * 使用 join（）方法的要点：
 * （1）join（）方法是实例方法，需要使用被合并线程的句柄（或者指针、变量）去调用，如
 * threadb.join( ) 。执行 threadb.join( ) 这 行 代 码 的 当 前 线 程 为 合 并 线 程 （ 甲 方 ） ， 进 入
 * TIMED_WAITING 等待状态，出让 CPU。 （2）如果设置了被合并线程的执行时间 millis（或者 millis+nanos），并不能保证当前线程一
 * 定会在 millis 时间后变为 RUNNABLE。 （3）如果主动方合并线程的在等待时被中断，则会抛出 InterruptedException 受检异常。
 */
public class JoinDemo {

    public static final int SLEEP_GAP = 2000;//睡眠时长,5s
    public static final int MAX_TURN = 10;//睡眠次数，稍微多点方便使用 Jstack
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
                for (int i = 1; i < MAX_TURN; i++)
                {
                    System.out.println(getName() + ", 睡眠轮次：" + i);
                    // 线程睡眠一会
                    Thread.sleep(SLEEP_GAP);
                }
            } catch (InterruptedException e)
            {
                System.out.println(getName() + " 发生异常被中断.");
            }
            System.out.println(getName() + " 运行结束.");
        }
    }
    public static void main(String args[])
    {
        Thread thread1 = new SleepThread();
        System.out.println("启动 thread1.");
        thread1.start();
        try
        {
            thread1.join();//合并线程 1，不限时
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("启动 thread2.");
        //启动第二条线程，并且进行限时合并，等待时间为 1 秒
        Thread thread2 = new SleepThread();
        thread2.start();
        try
        {
            thread2.join(1000);//限时合并，限时 1 秒
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("线程运行结束.");
    }



}
