package com.demo.one;

import java.util.concurrent.TimeUnit;

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

//		// 资源类
//		MyDate myData = new MyDate();
//
//		// AAA线程 实现了Runnable接口的，lambda表达式
//		new Thread(() -> {
//
//			System.out.println(Thread.currentThread().getName() + "\t come in");
//
//			// 线程睡眠3秒，假设在进行运算
//			try {
//				TimeUnit.SECONDS.sleep(3);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			// 修改number的值
//			myData.add();
//
//			// 输出修改后的值
//			System.out.println(Thread.currentThread().getName() + "\t update number value:" + myData.num);
//
//		}, "AAA").start();
//
//		// main线程就一直在这里等待循环，直到number的值不等于零
//		while(myData.num == 0) {
//			System.out.println("kkkkkkkkkkkk");
//		}
//
//		// 按道理这个值是不可能打印出来的，因为主线程运行的时候，number的值为0，所以一直在循环
//		// 如果能输出这句话，说明AAA线程在睡眠3秒后，更新的number的值，重新写入到主内存，并被main线程感知到了
//		System.out.println(Thread.currentThread().getName() + "\t mission is over");


	}




}


class ThreadDemo implements Runnable {

	private boolean flag = false;

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

//class MyDate{
//
//	 int num=0;
//	//volatile int num = 0;
//	public void add(){
//		this.num=60;
//	}
//
//
//}