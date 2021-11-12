package com.demo.test;

import lombok.SneakyThrows;

import java.util.concurrent.Executors;

/**
 * @author lkz
 * @date 2021/11/12 10:06
 * @description
 *  4. 守护线程的要点
 * 使用守护线程时，有以下需要特别注意的要点：
 * （1）守护线程必须在启动前，将其守护状态设置为 true；启动之后，不能再将用户线程设置
 * 为守护线程。否则，JVM 会抛出一个 InterruptedException 异常。
 * 具体来说，如果线程为守护线程，必须在线程实例的 start()方法调用之前，调用线程实例的
 * setDaemon（true），设置其 daemon 实例属性值为 true。 （2）守护线程存在被 JVM 强行终止的风险，所以，在守护线程中尽量不去访问系统资源，
 * 如文件句柄、数据库连接等等。守护线程被强行终止时，可能会引发系统资源操作的不负责任的
 * 中断，从而导致资源不可逆的损坏。
 * （3）守护线程创建的线程，也是守护线程。
 * 在守护线程中创建的线程，新的线程都是守护线程。在创建之后，如果通过调用
 * setDaemon(false)将新的线程显示的设置为用户线程，新的线程可以调整成为用户线程。
 * 5. 守护线程创建的线程也是守护线程
 */
public class DaemonDemo2 {

    public static final int SLEEP_GAP = 5000; //每一轮的睡眠时长
    public static final int MAX_TURN = 4; //线程执行轮次
    static class NormalThread extends Thread {
        static int threadNo = 1;
        public NormalThread() {
            super("normalThread-" + threadNo);
            threadNo++;
        }
        @SneakyThrows
        public void run() {
            for (int i = 0; ; i++)
            {
                Thread.sleep(SLEEP_GAP);
                System.out.println(getName() + ", 守护状态为:" + isDaemon());
            }
        }
    }
    public static void main(String args[]) throws InterruptedException {
        Thread daemonThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Thread normalThread = new NormalThread();
                //normalThread.setDaemon(false);
                normalThread.start();
            }
        }, "daemonThread");
        daemonThread.setDaemon(true);
        daemonThread.start();
        //这里，一定不能让 main 线程立即结束，否则看不到结果
        Thread.sleep(SLEEP_GAP);
        System.out.println(Thread.currentThread().getName() + " 运行结束.");
    }
}
