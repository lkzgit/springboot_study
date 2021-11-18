package com.demo.nien;

/**
 * @author lkz
 * @date 2021/11/11 16:11
 * @description 线程的优先级
 *
 * Thread 实例的 priority 属性默认是 5 级别，对应的类常量是 NORM_PRIORITY。优先级最大
 * 值为 10，最小值为 1，Thread 类中定义的三个优先级常量如下：
 * public static final int MIN_PRIORITY = 1;
 * public static final int NORM_PRIORITY = 5;
 * public static final int MAX_PRIORITY = 10;
 */
public class PriorityDemo {

    public static final int SLEEP_GAP = 1000;
    static class PrioritySetThread extends Thread {
        static int threadNo = 1;
        public PrioritySetThread() {
            super("thread-" + threadNo);
            threadNo++;
        }
        public long opportunities=0;
        public void run() {
            for (int i = 0; ; i++) {
                opportunities++;
            }
        }
    }
    public static void main(String args[]) throws InterruptedException {
        PrioritySetThread[] threads=new PrioritySetThread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i]=new PrioritySetThread();
            //优先级的设置，从 1-10
            threads[i].setPriority(i+1);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();//启动线程
        }
        Thread.sleep(SLEEP_GAP); //等待线程运行 1s
        for (int i = 0; i < threads.length; i++) {
            threads[i].stop(); //停止线程
        }
        for (int i = 0; i < threads.length; i++) {
            System.out.println(threads[i].getName()+
                    "-优先级为-"+threads[i].getPriority()+ //获取优先级
                    "-机会值为-"+threads[i].opportunities
            );
        }
    }



}
