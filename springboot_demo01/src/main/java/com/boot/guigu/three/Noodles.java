package com.boot.guigu.three;

/**
 * 虚假唤醒
 */
class Noodles{

    //面的数量
    private int num = 0;

    //做面方法
    public synchronized void makeNoodles() throws InterruptedException {
        //如果面的数量不为0，则等待食客吃完面再做面
        while (num != 0){
            this.wait();
        }

        num++;
        System.out.println(Thread.currentThread().getName()+"做好了一份面，当前有"+num+"份面");
        //面做好后，唤醒食客来吃
        this.notifyAll();
    }

    //吃面方法
    public synchronized void eatNoodles() throws InterruptedException {
        //如果面的数量为0，则等待厨师做完面再吃面
        while (num == 0){
            this.wait();
        }

        num--;
        System.out.println(Thread.currentThread().getName()+"吃了一份面，当前有"+num+"份面");
        //吃完则唤醒厨师来做面
        this.notifyAll();
    }

}

class TestNood {

    public static void main(String[] args) {

        Noodles noodles = new Noodles();

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10 ; i++) {
                        noodles.makeNoodles();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"厨师A").start();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10 ; i++) {
                        noodles.makeNoodles();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"厨师B").start();

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10 ; i++) {
                        noodles.eatNoodles();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"食客甲").start();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10 ; i++) {
                        noodles.eatNoodles();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"食客乙").start();

    }

}

