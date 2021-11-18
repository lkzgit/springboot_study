package com.demo.mangyThread;

import org.apache.tomcat.jni.Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.继承Thread类，重写run方法
 * 2.实现Runnable接口，重写run方法，实现Runnable接口的实现类的实例对象作为Thread构造函数的target
 * 3.通过Callable和FutureTask创建线程
 *
 * 创建线程的方式；
 *  1. 集成Thread类创建线程
 *  Thread类本质上是实现了Runnable接口的一个实例，代表一个线程的实例。
 *  启动线程的唯一方法就是通过Thread类的start()实例方法。
 *  start()方法是一个native方法，
 *  它将启动一个新线程，并执行run()方法。
 *  这种方式实现多线程很简单，通过自己的类直接extend Thread，
 *  并复写run()方法，就可以启动新线程并执行自己定义的run()方法
 */
public class MyThread extends Thread{

    public void run(){
        System.out.println("线程运行--------"+Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyThread th=new MyThread();
        MyThread th2=new MyThread();
        th.start();
        th2.start();
        ReentrantLock lock = new ReentrantLock();
        lock.unlock();
    }

}
