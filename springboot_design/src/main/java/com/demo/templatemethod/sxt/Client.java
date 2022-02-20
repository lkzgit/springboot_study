package com.demo.templatemethod.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO
 * @createTime 2022年02月20日 16:04:00
 */
public class Client  {

    public static void main(String[] args) {
            BanlTemplateMethod bank=new DrawMoney();
            bank.process();

    }
}

class  DrawMoney extends BanlTemplateMethod{
    @Override
    public void tran() {
        System.out.println("我要取钱");
    }
}