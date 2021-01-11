package com.boot.demo.test;

/**
 * start()和run()的区别
 * @param args
 */
public class Test1main {


    public static void main(String[] args) {


       Runnable runner=new Runnable();
        Thread th1= new Thread(runner,"新线程");
        //交换123的顺序 直接运行
        /**
         * 把3放到1和2前面执行，发现新线程的run()还是最后执行，说明它在就绪队列中排队，
         * 并没有这么快执行。而当前正在执行mian线程，
         * 每个线程执行都有一个时间片，main线程时间片还没结束，
         * 所以23先执行了。等main线程时间片用完，再执行新线程
         */
        //3
        System.out.println("th1.start():");
       th1.start();

        //区分以下三种情况
        //1
        System.out.println("th1.run():");
        th1.run();
        //2
        System.out.println("runner.run():");
        runner.run();
//        //3
//        System.out.println("th1.start():");
//        th1.start();
    }


}
