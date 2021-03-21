package com.demo.test;

import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  大厂面试 第二季
 *
 *  线程通信之生产者消费者阻塞队列
 */
public class MyResource {

        private volatile  boolean FLAG=true;//默认开启 进行生产消费

        private AtomicInteger atomicInteger=new AtomicInteger();

        BlockingQueue<String> blockingQueue=null;

    public MyResource(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;

        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProducer() throws Exception{
        String date=null;
        boolean retValue;
        //多线程条件下的判断 一定要使用While 防止出现虚假唤醒
        //当FLAG 为true的时候开始生产
        while (FLAG){
           date= atomicInteger.incrementAndGet()+"";

           //设置2秒存入一个
            retValue= blockingQueue.offer(date, 2L,TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t插入成功"+date);
            }else{
                System.out.println(Thread.currentThread().getName()+"\t插入失败"+date);
            }
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();

            }
        }
        System.out.println(Thread.currentThread().getName()+"\t 停止生产");
    }

    public void myConsumer() throws Exception{
        String retValue;
        // 多线程环境的判断，一定要使用while进行，防止出现虚假唤醒
        // 当FLAG为true的时候，开始生产
        while(FLAG) {
            // 2秒存入1个data
            retValue = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(retValue != null && retValue != "") {
                System.out.println(Thread.currentThread().getName() + "\t 消费队列:" + retValue  + "成功" );
            } else {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 消费失败，队列中已为空，退出" );

                // 退出消费队列
                return;
            }
        }
    }

    /**
     * 停止生产的判断
     */
    public void stop() {
        this.FLAG = false;
    }

}


class ProdConsumerBlockQueueDemo{

    public static void main(String[] args) {
        // 传入具体的实现类， ArrayBlockingQueue
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动\n\n");

            try {
                myResource.myProducer();
                System.out.println("\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "producer").start();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");

            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        // 5秒后，停止生产和消费
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("\n\n5秒中后，生产和消费线程停止，线程结束");
        myResource.stop();
    }

}
