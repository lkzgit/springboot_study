package com.demo.mediator;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Finacial.java
 * @Description TODO
 * @createTime 2022年02月20日 00:40:00
 */
public class Finacial implements DepartMent{

    private Mediator m; //持有中介者的引用

    public Finacial(Mediator m) {
        this.m = m;
        m.register("finacial",this);
    }

    @Override
    public void selfAction() {
        System.out.println("数钱");

    }

    @Override
    public void outAction() {
        System.out.println("Finacial 汇报工作 没钱了 支持工作");
    }
}
