package com.demo.two;

import java.util.Random;
import java.util.concurrent.*;

/*
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * 
 * 二、线程池的体系结构：
 * 	java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * 		|--**ExecutorService 子接口: 线程池的主要接口
 * 			|--ThreadPoolExecutor 线程池的实现类
 * 			|--ScheduledExecutorService 子接口：负责线程的调度
 * 				|--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 * 
 * 三、工具类 : Executors 
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 * public ScheduledFuture<?> scheduleAtFixedRate(
		 Runnable command, //异步任务 target 执行目标实例；
		 long initialDelay, //首次执行延时；
		 long period, //两次开始执行最小间隔时间；
		 TimeUnit unit //所设置的时间的计时单位，如 TimeUnit.SECONDS 常量；
	public ScheduledFuture<?> scheduleWithFixedDelay(
		 Runnable command,//异步任务 target 执行目标实例；
		 long initialDelay, //首次执行延时；
		 long delay, //前一次执行结束到下一次执行开始的间隔时间（间隔执行延迟时间）；
		 TimeUnit unit //所设置的时间的计时单位，如 TimeUnit.SECONDS 常量；
		);
 *
 */
public class TestScheduledThreadPool {

	public static void main(String[] args) throws Exception {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
		    try{
				for (int i = 0; i < 5; i++) {
					//pool.scheduleAtFixedRate() //延迟最大值 稳定定时器
					//pool.scheduleWithFixedDelay()// 任务+延迟 周期性 上次执行的时间 + 周期的时间+ 延迟的时间
					Future<Integer> result = pool.schedule(new Callable<Integer>(){ // 执行之后就不在执行

						@Override
						public Integer call() throws Exception {
							int num = new Random().nextInt(100);//生成随机数
							System.out.println(Thread.currentThread().getName() + " : " + num);
							return num;
						}

					}, 1, TimeUnit.SECONDS);

					System.out.println(result.get());
				}
		        }catch(Exception e){

		        }finally {
				pool.shutdown();
			}
	}
	
}



