package com.demo.observer.sxt.java;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO
 * @createTime 2022年02月20日 18:01:00
 */
public class Client
{

    public static void main(String[] args) {
        //创建目标对象
        ObServer2 obServer2 = new ObServer2();
        //创建观察者二
        JavaObserver a = new JavaObserver();
        JavaObserver b = new JavaObserver();
        JavaObserver c = new JavaObserver();
        obServer2.addObserver(a);
        obServer2.addObserver(b);
        obServer2.addObserver(c);
        obServer2.set(500);
        System.out.println(a.getMystate());
        System.out.println(b.getMystate());
        System.out.println(c.getMystate());
    }
}
