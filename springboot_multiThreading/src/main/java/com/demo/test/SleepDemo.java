package com.demo.test;

/**
 * @author lkz
 * @date 2021/11/11 16:56
 * @description
 */
public class SleepDemo {

    public static final int SLEEP_GAP = 5000;//睡眠时长,5s
    public static final int MAX_TURN = 50;//睡眠次数，稍微多点方便使用 Jstack
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
    public static void main(String args[]) throws InterruptedException
    {
        for (int i = 0; i < 5; i++)
        {
            Thread thread = new SleepThread();
            thread.start();
        }
        System.out.println(Thread.currentThread().getName() + " 运行结束.");
    }

}
