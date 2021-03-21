package com.demo.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 *
 * CyclicBarrier的字面意思就是可循环（Cyclic）使用的屏障（Barrier）。
 * 它要求做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
 * 直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活，
 * 线程进入屏障通过CyclicBarrier的await方法。
 *
 * CyclicBarrier与CountDownLatch的区别：
 *  CyclicBarrier可重复多次，而CountDownLatch只能是一次。
 */
public class SummonTheDragonDemo {
    public static void main(String[] args) {
        /**
         * 定义一个循环屏障，参数1：需要累加的值，参数2 需要执行的方法
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            final Integer tempInt = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到 第" + tempInt + "颗龙珠");

                try {
                    // 先到的被阻塞，等全部线程完成后，才能执行方法
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}

