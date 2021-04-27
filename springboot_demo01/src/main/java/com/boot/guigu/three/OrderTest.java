package com.boot.guigu.three;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 顺序打印
 *
 */
public class OrderTest {

    public static void main(String[] args) {
        ABC abc=new ABC();
        new Thread(()->{ for (int i = 1; i <=10; i++)abc.A(); },"线程A").start();
        new Thread(()->{ for (int i = 1; i <=10; i++)abc.B(); },"线程b").start();
        new Thread(()->{ for (int i = 1; i <=10; i++)abc.C(); },"线程C").start();


    }

}

class ABC{


    private int number=1;

    Lock lock=new ReentrantLock();
    Condition condition1=lock.newCondition();
    Condition condition2=lock.newCondition();
    Condition condition3=lock.newCondition();

    public void A(){
            try{
                lock.lock();
                while (number!=1){
                    condition1.await();
                }
                System.out.println("输出打印A");
                number=2;
                condition2.signal();
            }catch(Exception e){

            }finally {
                lock.unlock();
            }
    }
    public void B(){
        try{
            lock.lock();
            while (number!=2){
                condition2.await();
            }
            System.out.println("输出打印B");
            number=3;
            condition3.signal();
        }catch(Exception e){

        }finally {
            lock.unlock();
        }
    }
    public void C(){
        try{
            lock.lock();
            while (number!=3){
                condition3.await();
            }
            System.out.println("输出打印C");
            number=1;
            condition1.signal();
        }catch(Exception e){

        }finally {
            lock.unlock();
        }
    }

}