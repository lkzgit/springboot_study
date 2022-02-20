package com.demo.templatemethod.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName BanlTemplateMethod.java
 * @Description TODO
 * @createTime 2022年02月20日 16:06:00
 */
public abstract class BanlTemplateMethod {

    //具体方法
    public void takeNumber(){
        System.out.println("取号排队");
    }
    //具体业务
    public abstract void tran();//办具体业务  钩子方法

    public void exec(){
        System.out.println("---exec 反馈");
    }
    public  final void process(){ //模板方法
        this.takeNumber();
        this.tran();
        this.exec();
    }

}
