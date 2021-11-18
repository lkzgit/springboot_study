package com.demo.nien;

import sun.misc.Unsafe;

import java.security.acl.Owner;

/**
 * @author lkz
 * @date 2021/11/16 20:54
 * @description 等待唤醒
 */
public class WaitNotifyDemo {

    static Object locko = new Object();
    //等待线程的异步目标任务
    static class WaitTarget implements Runnable
    {
        public void run()
        {
            //加锁
            synchronized (locko)
            {
                try
                {
                    //启动等待
                    System.out.println(("启动等待"));
                    //等待被通知，同时释放 locko 监视器的 Owner 权限
                    locko.wait();
                    //收到通知后，线程会进入 locko 监视器的 EntryList
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                //获取到监视器的 Owner 权利
                System.out.println(("收到通知，当前线程继续执行"));
            }
        }
    }
    //通知线程的异步目标任务
    static class NotifyTarget implements Runnable
    {
        public void run()
        {
            //加锁
            synchronized (locko)
            {
                //从屏幕读取输入，目的阻塞通知线程，方便使用 jstack 查看线程状态
                Thread.interrupted();
                //获取 lock 锁，然后进行发送
                // 此时不会立即释放 locko 的 Monitor 的 Owner，需要执行完毕
                locko.notifyAll();
                System.out.println(("发出通知了，但是线程还没有立马释放锁"));
            }
        }
    }
    public static void main(String[] args) throws InterruptedException
    {
        //创建等待线程
        Thread waitThread = new Thread(new WaitTarget(), "WaitThread");
        //启动等待线程
        waitThread.start();

        Thread.sleep(1000);
        //创建通知线程
        Thread notifyThread = new Thread(new NotifyTarget(),
                "NotifyThread");
        //启动通知线程
        notifyThread.start();
    }

}
