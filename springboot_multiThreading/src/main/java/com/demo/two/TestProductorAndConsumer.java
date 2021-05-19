package com.demo.two;

/*
 * 生产者和消费者案例 虚假唤醒  synchronized实现
 */
public class TestProductorAndConsumer {

	public static void main(String[] args) {
		Clerk1 clerk = new Clerk1();
		
		Productor1 pro = new Productor1(clerk);
		Consumer1 cus = new Consumer1(clerk);
		
		new Thread(pro, "生产者 A").start();
		new Thread(cus, "消费者 B").start();
		
		new Thread(pro, "生产者 C").start();
		new Thread(cus, "消费者 D").start();
	}
	
}

//店员
class Clerk1{
	private int product = 0;

	//进货
	public synchronized void get(){//循环次数：0
		while (product >= 1){//为了避免虚假唤醒问题，应该总是使用在循环中
			System.out.println("产品已满！");

			try {
				this.wait(); //等待
			} catch (InterruptedException e) {
			}

		}

		System.out.println(Thread.currentThread().getName() + " : " + ++product);
		this.notifyAll(); //唤醒 能够持续唤醒
	}

	//卖货
	public synchronized void sale(){//product = 0; 循环次数：0
		while(product <= 0){ //使用while 线程唤醒 重新判断一次
			System.out.println("缺货！");

			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}

		System.out.println(Thread.currentThread().getName() + " : " + --product);
		this.notifyAll();
	}
}

//生产者
class Productor1 implements Runnable{
	private Clerk1 clerk;

	public Productor1(Clerk1 clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}

			clerk.get();
		}
	}
}

//消费者
class Consumer1 implements Runnable{
	private Clerk1 clerk;

	public Consumer1(Clerk1 clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}
}