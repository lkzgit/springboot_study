package com.demo.strategy.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO 策略模式
 * @createTime 2022年02月20日 15:42:00
 *  策略模式 对应于解决某一个问题的算法族，允许用户从该算法族中任选一个来解决某个问题
    同时可以方便的更换算法或者增加新的算法。
    应用;  Spring框架 Resource接口 资方访问策略
        JAVA.service.http.httpServlet#service

 */
public class Client {

    public static void main(String[] args) {
        Strategy s1=new NewCustomerOldStrategy();
        Context context = new Context(s1);
        context.pringPrice(100);
    }
}
