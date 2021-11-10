package com.demo.mangyThread;

import java.util.concurrent.*;

public class ThreadPool {

    public static void main(String[] args) {

     //   ExecutorService pool = Executors.newFixedThreadPool(5);//固定数量线程池大小
     //   ExecutorService pool = Executors.newSingleThreadExecutor();//一个固定的线程池大小
     //   ExecutorService pool = Executors.newCachedThreadPool();//可伸缩的 遇强则强,遇弱则弱
        ExecutorService pool=new ThreadPoolExecutor(2,5,3,TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try{
            for(int i=1;i<=15;i++){
              pool.execute(new RunThread());
            }
        }catch (Exception e){

        }finally {
            pool.shutdown();
        }
        System.out.println("0000000000");

    }

}
