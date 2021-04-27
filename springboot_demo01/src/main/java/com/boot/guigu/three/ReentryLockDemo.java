package com.boot.guigu.three;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 */
 class ReentryLockDemo {

     synchronized void test1(){
         System.out.println("======外");
         test2();
     }
     synchronized  void test2(){
         System.out.println("-----中");
         test3();
     }
     synchronized  void test3(){
         System.out.println("-------内");

     }

    public static void main(String[] args) {
        //new ReentryLockDemo().test1();
        new Thread(()->{
            Lock lock=new ReentrantLock();
                try{
                    lock.lock();
                    System.out.println("*****lock1*****");
                        try{
                            lock.lock();
                            System.out.println("--------lock2");

                        }catch(Exception e){

                        }finally {
                            lock.unlock();
                        }
                    }catch(Exception e){

                    }finally {
                    lock.unlock();
                }
        },"start").start();
    }

}


