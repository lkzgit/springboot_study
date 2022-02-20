package com.demo.mediator;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Development.java
 * @Description TODO
 * @createTime 2022年02月20日 00:40:00
 */
public class Development implements DepartMent{

    private Mediator m; //持有中介者的引用

    public Development(Mediator m) {
        this.m = m;
        m.register("development",this);

    }

    @Override
    public void selfAction() {
        System.out.println("专心开发项目");

    }

    @Override
    public void outAction() {
        System.out.println("汇报工作 没钱了 支持工作");
        m.command("finacial");

    }
}
