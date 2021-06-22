package com.demo.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替输出 a,b,c
 */
public class ManyThread {

    private static Thread a,b,c;

    public static void main(String[] args) {
        ABCDemo abcDemo=new ABCDemo();
      a=  new Thread(()->{
          for(int i=1;i<=10;i++){
              abcDemo.testA();
          }

        },"A线程");
      a.start();

      b=  new Thread(()->{
          for(int i=1;i<=10;i++){
              abcDemo.testB();
          }
        },"B线程");
      b.start();

      c=  new Thread(()->{
          for(int i=1;i<=10;i++){
              abcDemo.testC();
          }
        },"C线程");
      c.start();

    }
}

class ABCDemo{

    private int num=1;//标记当前正在执行的线程
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void testA(){
        lock.lock();
        try {

            if(num!=1){
                condition1.await();
            }

                System.out.println("我是A");


            num=2;
            condition2.signal();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }

    public void testB(){
        lock.lock();
        try {
            if(num!=2){
                condition2.await();
            }

                System.out.println("我是B");


            num=3;
            condition3.signal();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }
    public void testC(){
        lock.lock();
        try {

            if(num!=3){
                condition3.await();
            }

                System.out.println("我是C");


            num=1;
            condition1.signal();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }


}
