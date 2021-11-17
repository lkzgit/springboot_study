package com.demo.test;


import com.demo.util.JvmUtil;
import com.demo.util.ThreadUtil;
import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lkz
 * @date 2021/11/17 16:40
 * @description 模拟cas
 */
public class TestCompareAndSwap {
    // 模拟CAS 算法
    static class OptimisticLockingPlus
    {
        private static final int THREAD_COUNT = 10;

        //值
        volatile private int value;

        //不安全类
        private static final Unsafe unsafe = JvmUtil.getUnsafe();

        //value 的内存偏移（相对于对象头）
        private static final long valueOffset;

        private static final AtomicLong failure = new AtomicLong(0);

        static
        {
            try
            {
                //取得内存偏移
                valueOffset = unsafe.objectFieldOffset(
                        OptimisticLockingPlus.class.getDeclaredField("value"));

                System.out.println("valueOffset:=" + valueOffset);
            } catch (Exception ex)
            {
                throw new Error(ex);
            }
        }

        public final boolean unSafeCompareAndSet(int oldValue, int newValue)
        {
            return unsafe.compareAndSwapInt(this, valueOffset, oldValue, newValue);
        }


        // 无锁编程：安全的自增方法
        public void selfPlus()
        {
            // 获取旧值
            int oldValue = value;
            int i = 0;
            //如果操作失败则自旋，一直到操作成功
            do
            {
                oldValue = value;
                //统计无效的自旋次数
                if (i++ > 1)
                {
                    failure.incrementAndGet();
                }

            } while (!unSafeCompareAndSet(oldValue, oldValue + 1));
        }


        public static void main(String[] args) throws InterruptedException
        {
            final OptimisticLockingPlus cas = new OptimisticLockingPlus();
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
            for (int i = 0; i < THREAD_COUNT; i++)
            {
                // 创建10个线程,模拟多线程环境
                ThreadUtil.getMixedTargetThreadPool().submit(() ->
                {

                    for (int j = 0; j < 1000; j++)
                    {
                        cas.selfPlus();
                    }
                    latch.countDown();

                });
            }
            latch.await();
            System.out.println("累加之和：" + cas.value);
            System.out.println("失败次数：" + cas.failure.get());
        }
    }


}
