package com.demo.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * @author lkz
 * @date 2021/11/11 17:24
 * @description
 */
public class InterruptDemo2 {

    //测试用例：获取异步调用的结果
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread()
        {
            @SneakyThrows
            public void run()
            {
                System.out.println("线程启动了");
                //一直循环
                while (true)
                {
                    System.out.println(isInterrupted());
                    Thread.sleep(5000);

                    //如果线程被中断，退出死循环
                    if (isInterrupted())
                    {
                        System.out.println("线程结束了");
                        return;
                    }
                }
            }
        };
        thread.start();
        Thread.sleep(2000);//等待 2 秒
        thread.interrupt(); //中断线程
        Thread.sleep(2000);//等待 2 秒
        thread.interrupt();
    }


}
