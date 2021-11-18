package com.demo.nien;

import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author lkz
 * @date 2021/11/18 18:50
 * @description CLH队列锁
 */
public class CLHLock implements Lock {

    /**
     * 指向当前节点
     */
    private static ThreadLocal<Node> curNodeLocal = new ThreadLocal();
    private  String name;
    /**
     * CLHLock队列的尾部
     */
    private AtomicReference<Node> tail = new AtomicReference<>(null);

    public CLHLock()
    {
        //设置尾部节点
        tail.getAndSet(Node.EMPTY);
    }

    public CLHLock(String name)
    {
        this.name=name;
        //设置尾部节点
        tail.getAndSet(Node.EMPTY);
    }
    //加锁：将节点添加到等待队列的尾部
    @Override
    public void lock()
    {
        Node curNode = new Node(true, null);
        Node preNode = tail.get();
        //CAS自旋：将当前节点插入到队列的尾部
        while (!tail.compareAndSet(preNode, curNode))
        {
            preNode = tail.get();
        }
        //设置前驱
        curNode.setPrevNode(preNode);

        //监听前驱节点的locked变量，直到其值为false
        // 若前继节点的locked状态为true，则表示前一线程还在抢占或者占有锁
        while (curNode.getPrevNode().isLocked())
        {
            //让出CPU时间片，提高性能
            Thread.yield();
        }
        // 能执行到这里，说明当前线程获取到了锁
        //  Print.tcfo("获取到了锁！！！");

        //设置在线程本地变量中，用于释放锁
        curNodeLocal.set(curNode);
    }

    @Override
    public void unlock()
    {
        Node curNode = curNodeLocal.get();
        curNode.setPrevNode(null);//help for GC
        curNodeLocal.set(null);
        curNode.setLocked(false);
    }

    @Data
    static class Node
    {
        public Node(boolean locked, Node prevNode)
        {
            this.locked = locked;
            this.prevNode = prevNode;
        }

        //true：当前线程正在抢占锁、或者已经占有锁
        // false：当前线程已经释放锁，下一个线程可以占有锁了
        volatile boolean locked;
        //前一个节点，需要监听其locked字段
        Node prevNode;


        //空节点
        public static final Node EMPTY =
                new Node(false, null);
    }



    @Override
    public void lockInterruptibly() throws InterruptedException
    {
        throw new IllegalStateException(
                "方法 'lockInterruptibly' 尚未实现!");
    }


    @Override
    public boolean tryLock()
    {
        throw new IllegalStateException(
                "方法 'tryLock' 尚未实现!");

    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
    {
        throw new IllegalStateException(
                "方法 'tryLock' 尚未实现!");
    }



    @Override
    public Condition newCondition()
    {
        throw new IllegalStateException(
                "方法 'newCondition' 尚未实现!");
    }

    @Override
    public String toString()
    {
        return "CLHLock{" + name +    '}';
    }

}
