package com.boot.guigu.three;

/**
 * 模拟抢票
 */
public class SellTicket implements Runnable {

    private static int tickets = 100;
    private Object obj = new Object();
    private int x = 0;

    @Override
    public void run() {
        while (true) {
            if (x % 2 == 0) {

                synchronized (SellTicket.class) {
                    if (tickets > 0) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "正在出售第" + tickets + "张票");
                        tickets--;
                    }
                }
            } else {

                sellTicket();
            }
            x++;
        }
    }

    private static synchronized void sellTicket() {
        if (tickets > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在出售第" + tickets + "张票");
            tickets--;
        }
    }
}

class Sell{
    public static void main(String[] args) {
        new Thread(new SellTicket(),"A").start();
        new Thread(new SellTicket(),"B").start();
        new Thread(new SellTicket(),"C").start();
        new Thread(new SellTicket(),"d").start();
        new Thread(new SellTicket(),"e").start();
        new Thread(new SellTicket(),"f").start();
    }
}