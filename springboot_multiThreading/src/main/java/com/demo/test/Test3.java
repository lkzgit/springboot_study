package com.demo.test;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author lkz
 * @date 2021/10/20 16:49
 * @description 交替输出求书
 */
public class Test3 {

    public static void main(String[] args) {
        final Test3 test3=new Test3();
        Thread t1 = new Thread(test3::print1);
        Thread t2 = new Thread(test3::print2);

        t1.start();
        t2.start();

    }

    public synchronized void print1(){
        for(int i=0;i<50;i+=2){
            System.out.println(i);
            this.notify();
            try {
                this.wait();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void print2(){
        for(int i=1;i<50;i+=2){
            System.out.println(i);
            this.notify();
            try {
                this.wait();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
