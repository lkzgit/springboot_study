package com.demo.nien;

/**
 * @author lkz
 * @date 2021/11/19 16:22
 * @description
 */
public class RunTest implements Runnable{
    @Override
    public void run() {
        System.out.println("这是一个测试的数据"+Thread.currentThread().getName());
    }
}
