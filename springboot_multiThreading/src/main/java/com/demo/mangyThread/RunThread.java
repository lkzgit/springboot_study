package com.demo.mangyThread;

public class RunThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        }catch (Exception e){

        }
        System.out.println("Runnable:"+Thread.currentThread().getName());
    }


    public static void main(String[] args) {
        RunThread th=new RunThread();
        Thread thread = new Thread(th);
        thread.start();
    }
}
