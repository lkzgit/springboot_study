package com.demo.factoryPattern.abstractFactory;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Engine.java
 * @Description TODO
 * @createTime 2022年02月15日 23:46:00
 */
public interface Engine {
    void run();
    void start();
}
//高级
 class LuxuryEngine implements Engine{

     @Override
     public void run() {
         System.out.println("跑的快");
     }

     @Override
     public void start() {
         System.out.println("启动快");
     }
 }
 //低级
class lowEngine implements Engine{
     @Override
     public void run() {
         System.out.println("跑的慢");
     }

     @Override
     public void start() {
         System.out.println("启动慢");
     }
 }