package com.boot.guigu;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    public static void main(String[] args) {
        Object resourceA = new Object();
        Object resourceB = new Object();

        new Thread(new MyTask(resourceA, resourceB),"Thread A").start();
        new Thread(new MyTask(resourceB, resourceA),"Thread B").start();
    }



}
 class MyTask implements  Runnable{

     private Object resourceA, resourceB;

     public MyTask(Object resourceA, Object resourceB) {
         this.resourceA = resourceA;
         this.resourceB = resourceB;
     }

     @Override
     public void run() {
         synchronized (resourceA) {
             System.out.println(String.format("%s 自己持有%s，尝试持有%s",//
                     Thread.currentThread().getName(), resourceA, resourceB));

             try {
                 TimeUnit.SECONDS.sleep(2);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

             synchronized (resourceB) {
                 System.out.println(String.format("%s 同时持有%s，%s",//
                         Thread.currentThread().getName(), resourceA, resourceB));
             }
         }
     }

 }