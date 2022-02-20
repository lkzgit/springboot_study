package com.demo.observer.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO
 * @createTime 2022年02月20日 17:33:00
 */
public class Client {

    public static void main(String[] args) {
        SubA subA = new SubA();
        //创建多个观察者对象
        ObserverA a = new ObserverA();
        ObserverA b = new ObserverA();
        ObserverA c = new ObserverA();
        subA.regis(a);
        subA.regis(b);
        subA.regis(c);

        //修改状态

        subA.setState(300);
        System.out.println("----------");
        System.out.println(a.getMysate());
        System.out.println(b.getMysate());
        System.out.println(c.getMysate());
        //
    }

}
