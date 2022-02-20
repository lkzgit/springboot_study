package com.demo.mediator;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO 中介者模式
 * 简单来说 一对多模式
 * @createTime 2022年02月20日 00:23:00
 * 应用； mvc 模式
 */
public class Client {

    public static void main(String[] args) {
        Mediator m=new President();
        Development de=new Development(m);
        Finacial f=new Finacial(m);

        de.outAction();
        de.selfAction();
    }
}
