package com.boot.guigu.three;

/**
 * 唤醒
 */
public class LockSupportDemo {

    static Object object=new Object();

    public static void main(String[] args) {
        //锁住代码块
        new Thread(()->{
            synchronized (object){
                System.out.println(Thread.currentThread().getName()+"\t"+"----come in");
                try {
                    object.wait();
                }catch (Exception e){

                }
                System.out.println(Thread.currentThread().getName()+"\t"+"*******被唤醒");
            }
        },"A").start();
        new Thread(()->{
            synchronized (object){

                try {
                    object.notify();
                    System.out.println(Thread.currentThread().getName()+"\t"+"*******通知");
                }catch (Exception e){

                }
            }
        },"D").start();
    }
}
