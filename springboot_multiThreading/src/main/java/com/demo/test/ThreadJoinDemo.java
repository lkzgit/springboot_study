package com.demo.test;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lkz
 * @date 2021/11/12 9:07
 * @description
 */
public class ThreadJoinDemo {

    public static void main(String[] sure) throws InterruptedException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t begin");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("t finished");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2 begin");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("t2 finished");
            }
        });
        long start = System.currentTimeMillis();
        t.start();
        //主线程等待线程t执行完成
        t2.join(5000);
        System.out.println("t的线程状态："+t.getState());
        System.out.println("t2的线程状态："+t2.getState());

        System.out.println("执行时间:" + (System.currentTimeMillis() - start));
        System.out.println("Main finished");



    }

}
