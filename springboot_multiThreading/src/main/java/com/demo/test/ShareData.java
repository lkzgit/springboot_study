package com.demo.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShareData {

    private int num=0;

    private Lock lock=new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    public void increment() throws Exception{
        lock.lock();
        try {
            while (num!=0){
                condition1.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"\t"+num);
            //唤醒
            condition1.signalAll();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }

    public void lose() throws Exception{
        lock.lock();
        try {
            while (num==0){ //必须要用while判断 多线程条件下
                condition1.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+"\t"+num);
            //唤醒
            condition1.signalAll();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }


}


/**
 * 题目 一个初始值变量为0 两个线程对其交替操作一个加 一个减
 */

class ProdConsumer_TradtionDemo{

    public static void main(String[] args) {

        ShareData shareData=new ShareData();
        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"tianjia").start();


        new Thread(()->{
            for(int i=0;i<5;i++){
                try {
                    shareData.lose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"jianshao").start();

    }
}