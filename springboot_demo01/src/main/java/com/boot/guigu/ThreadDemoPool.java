package com.boot.guigu;

import java.util.concurrent.*;

public class ThreadDemoPool {

    public static void main(String[] args) {
        ExecutorService pool=new ThreadPoolExecutor(2,5,1l, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        try{
            for(int i=0;i<100;i++){
                final int t=i;
                pool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"当前办理"+t);
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }

    }
}
