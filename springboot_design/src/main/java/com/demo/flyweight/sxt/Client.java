package com.demo.flyweight.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO 享元模式
 * @createTime 2022年02月19日 21:52:00
 *  场景； 如果存在很多个完全相同或者相似的对象 我们可以通过享元模式 节省内存
 *  核心；
 *   享元模式 以共享的方式高效的支持大量细粒度对象的引用
 *   享元对象能做到共享的关键是区分了内部状态和外部状态
 *      内部状态；可以共享不会随环境变化而改变
 *      外部状态；不可以共享 会随环境的变化而变化
 *  实现；
 *      FlyweightFactory享元工厂类： 创建并管理享元对象,享元池一般设计成键值对
 *      FlyWeight抽象享元类：通常是一个接口或抽象类,声明公共方法,这些方法可以向外界提供对象的内部状态,设置外部状态。
 *      ConcreteFlyWeight具体享元类：为内部状态提供成员变量进行存储
 *      UnsharedConcreteFlyWeight共享享元类：不能被共享的子类可以设计为非共享享元类
 *  场景； 线程池 数据库连接池,String 类的设计
 *
 *
 *
 */
public class Client {

    public static void main(String[] args) {
        ChessFlyWeight c1 = ChessFactory.getChess("黑色");
        ChessFlyWeight c2 = ChessFactory.getChess("黑色");
        System.out.println("两者共享");
        System.out.println(c1);
        System.out.println(c2);

        System.out.println("不共享------");
        c1.display(new Coordinate(1,2));
        c2.display(new Coordinate(2,2));
        System.out.println(c1);
        System.out.println(c2);
    }
}
