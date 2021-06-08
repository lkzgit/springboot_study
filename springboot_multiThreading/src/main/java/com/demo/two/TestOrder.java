package com.demo.two;

import java.util.concurrent.locks.LockSupport;

public class TestOrder {

    private static Thread a=null,b=null;

    public static void main(String[] args) {


        SyncWaitNotify syncWaitNotify = new SyncWaitNotify(1, 5);
        new Thread(() -> {
            syncWaitNotify.print(1, 2, "a");
        }).start();
        new Thread(() -> {
            syncWaitNotify.print(2, 3, "b");
        }).start();
        new Thread(() -> {
            syncWaitNotify.print(3, 1, "c");
        }).start();

//        char[] aArr="abcdefg".toCharArray();
//        char[] numArr="1234567".toCharArray();
//
//        a=new Thread(()->{
//            for(char a: aArr){
//                System.out.println(a);
//                LockSupport.unpark(b);
//                LockSupport.park();
//            }
//        },"a");
//        b=new Thread(()->{
//            for(char n: numArr){
//                LockSupport.park();
//                System.out.println(n);
//
//                LockSupport.unpark(a);
//            }
//        },"b");
//        a.start();
//        b.start();

//        Thread t1= new Thread(()->{
//            LockSupport.park();
//            System.out.println("1");
//         },"t1");
//        t1.start();
//
//        Thread t2= new Thread(()->{
//            LockSupport.park();
//            System.out.println("2");
        //LockSupport.unpark(t1);
//        },"t2");
//        t2.start();
//


    }
}

class SyncWaitNotify {
    private int flag;
    private int loopNumber;
    public SyncWaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
    public void print(int waitFlag, int nextFlag, String str) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (this.flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
