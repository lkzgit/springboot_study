package com.demo.one;

/*
 * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 					  相较于 synchronized 是一种较为轻量级的同步策略。
 * synchronized ()加锁 多线程 会造成线程阻塞 ，会被挂起等待下一次cpu分配
 * 	同时请求加锁模块，判断是否有锁 如果有锁则会进入到队列等待释放
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 */
public class TestVolatile {
	
	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();
		new Thread(td).start();
		
		while(true){

				if(td.isFlag()){
					System.out.println("------------------");
					break;
				}

		}
		
	}

}

class ThreadDemo implements Runnable {

	private volatile boolean flag = false;


	@Override
	public void run() {
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

		flag = true;
		
		System.out.println("flag=" + isFlag());

	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}