package com.demo.factoryPattern.abstractFactory;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName client.java
 * @Description TODO
 * @createTime 2022年02月16日 00:02:00
 *  工厂模式要点；
 *      简单工厂(静态工厂模式)
 *      虽然某种程度不符合设计原则 但实际使用最多
 *      工厂方法模式
 *      不修改已有类的前提下，通过增加新的工厂类实现扩展
 *      抽象工厂模式 要有产品族的概念
 *      不可以增加产品 ，可以增加产品族
 *   应用场景
 *   JDK中Calendar的getinstance 方法
 *   JDBC中Connection连接方法
 *   Hibernate中SessionFACTORY 创建Session
 *   spring 中ioc容器创建管理bean对象
 *   xml解析是DocmentBuilderFactory创建解析器对象
 *   反射中class对象的newInstance()
 *
 *
 */
public class client {

    public static void main(String[] args) {
        CarFactory luxuryFactory = new LuxuryFactory();
        Engine engine = luxuryFactory.createEngine();
        engine.run();
        engine.start();
    }
}
