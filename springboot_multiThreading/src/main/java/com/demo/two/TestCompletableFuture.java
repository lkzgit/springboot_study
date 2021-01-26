package com.demo.two;

import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 测试异步调用 CompletableFuture
 */
public class TestCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 没有返回值的runAsync
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            try{
//                TimeUnit.SECONDS.sleep(2);
//            }catch (Exception e){
//
//            }
//            System.out.println(Thread.currentThread().getName());
//
//        });
//        System.out.println("dddd");
//        future.get();//获取异步回调

        // 返回的是错误信息；
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {

                System.out.println(Thread.currentThread().getName()+"supplyAsync=>Integer");
               // int i=10/0;
                return 1024;
        });

       future.whenComplete((t,u)->{
           System.out.println("t=>"+t);//正常的返回结果
           System.out.println("u=>"+u);//错误信息
       }).exceptionally((e)->{
           System.out.println(e.getMessage());
           return 233;//可以获取到错误的返回结果
       }).get();



    }

}
