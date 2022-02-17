package com.demo.adapter.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO 测试类
 * @createTime 2022年02月17日 22:33:00
 */
public class Client {

    public void test(Target t){
        t.target();
    }

    public static void main(String[] args) {
        Client client = new Client();
        Adpatee adpatee = new Adpatee();
        Target target = new Adpater(adpatee);

        client.test(target);
    }
}
