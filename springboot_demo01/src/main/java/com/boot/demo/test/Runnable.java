package com.boot.demo.test;

public class Runnable implements java.lang.Runnable {

    private int food = 10;

    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("线程名：" + Thread.currentThread().getName() + " food:" + food--);
        }
    }
}
