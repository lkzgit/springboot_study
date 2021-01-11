package com.boot.demo.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用AtomicInteger原子类
 */
public class VolatileNoAtomic2 extends Thread{

    static int count = 0;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            //等同于i++
            atomicInteger.incrementAndGet();
        }
        System.out.println(count);
    }

    public static void main(String[] args) {
        // 初始化10个线程
        VolatileNoAtomic[] volatileNoAtomic = new VolatileNoAtomic[10];
        for (int i = 0; i < 10; i++) {
            // 创建
            volatileNoAtomic[i] = new VolatileNoAtomic();
        }
        for (int i = 0; i < volatileNoAtomic.length; i++) {
            volatileNoAtomic[i].start();
        }
    }

}
