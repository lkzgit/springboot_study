package com.demo.mangyThread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorThread {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("----程序开始运行-----");
        Date date1=new Date();

        int taskSize=6;//设置线程池大小
        /**
         * 创建固定数目线程的线程池。
         * public static ExecutorService newFixedThreadPool(int nThreads)
         * 创建一个可缓存的线程池，调用execute 将重用以前构造的线程（如果线程可用）。
         *  如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60 秒钟未被使用的线程。
         * public static ExecutorService newCachedThreadPool()
         * 创建一个单线程化的Executor。
         * public static ExecutorService newSingleThreadExecutor()
         * 创建一个支持定时及周期性的任务执行的线程池，多数情况下可用来替代Timer类。
         * public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
         *
         * ExecutoreService提供了submit()方法，传递一个Callable，或Runnable，返回Future。
         * 如果Executor后台线程池还没有完成Callable的计算，这调用返回Future对象的get()方法，会阻塞直到计算完成。
         */

        //创建线程池
        //ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        ExecutorService pool = Executors.newCachedThreadPool();
        //创建多个有返回值的任务
        List<Future> list=new ArrayList<Future>();
        for(int i=0;i<taskSize;i++){
            //调用线程
            Callable c=new MyCallable(i+" ");
            //执行任务并获取Future对象
            Future submit = pool.submit(c);
            list.add(submit);
        }
        //关闭线程池
        pool.shutdown();

        //获取所有并发任务的结果
        for(Future f:list){
            //从Future 对象上获取任务的返回值，并打印
            System.out.println("并发任务结果>>>"+f.get().toString());
        }
        Date date2=new Date();
        System.out.println("----程序结束运行----，程序运行时间【"
                + (date2.getTime() - date1.getTime()) + "毫秒】");
    }

    static class MyCallable implements Callable<Object>{

        private String taskNum;

        public MyCallable(String taskNum) {
            this.taskNum = taskNum;
        }

        @Override
        public Object call() throws Exception {
            System.out.println("线程任务:"+taskNum+"任务启动");
            Date dateTmp1=new Date();
            Thread.sleep(1000);
            System.out.println("业务处理------");
            Date dateTmp2=new Date();
            long time=dateTmp2.getTime()-dateTmp1.getTime();
            System.out.println("线程任务"+taskNum+"任务结束 ");
            return taskNum+"任务返回运行结果，当前任务时间:"+time+"毫秒";
        }
    }
}
