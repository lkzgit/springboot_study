package com.demo.test;


import java.util.concurrent.CountDownLatch;

/**
 * 尚硅谷大厂面试
 * 让一线程阻塞直到另一些线程完成一系列操作才被唤醒。
 *
 * CountDownLatch主要有两个方法（await()，countDown()）。
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        // 计数器
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for(int i=1;i<=6;i++){
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 上完自习，离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t 班长最后关门");

    }


}
