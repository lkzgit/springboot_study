package com.demo.state;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO
 * @createTime 2022年02月20日 16:38:00
 *  状态模式；
 *      核心；用于解决系统中复杂对象的状态转换以及不同状态下的行为封装问题
 *     结构；
 *      Context 环境类；环境中维护一个State对象他是定义了当前的状态
 *      State抽象状态类
 *      ConcreteState具体状态类；每一个封装了一个状态对应的行为
 *
 */
public class Client {

    public static void main(String[] args) {
        //操作对象
        Context context = new Context();
        //设置状态
        context.setState(new FreeState());

    }
}
