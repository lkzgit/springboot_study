package com.demo.two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 一、用于解决多线程安全问题的方式：
 * 
 * synchronized:隐式锁
 * 1. 同步代码块
 * 
 * 2. 同步方法
 * 
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 */
public class TestLock {
	
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		
//		new Thread(ticket, "1号窗口").start();
//		new Thread(ticket, "2号窗口").start();
//		new Thread(ticket, "3号窗口").start();
		//创建五个线程 等同于上面new Thread() 操作的同一个线程对象
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for(int i=0;i<50;i++){ //模拟50个请求
			pool.submit(ticket);
		}

	}

}

class Ticket implements Runnable{
	
	private int tick = 100;
	
	private Lock lock = new ReentrantLock();





	@Override
	public void run() {
		while(true){
			
			//lock.lock(); //上锁
			synchronized (this){
				try{
					if(tick > 0){
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
						}

						System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + tick--);
					}
				}finally{
					//lock.unlock(); //释放锁
				}
			}

		}
	}
	
}