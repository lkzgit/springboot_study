package com.demo.chainofresponsibility.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO 责任链模式
 * @createTime 2022年02月19日 23:06:00
 */
public class Client {

    public static void main(String[] args) {
        Leader a=new Director("zhuren");
        Leader b=new Manager("经理");
        Leader c=new GeneraManager("ZONGJIAN");
        //设置责任链对象的关系
        a.setNextLeader(b);
        b.setNextLeader(c);

        // 请求操作
        LeaveRequest request=new LeaveRequest("TOM",50,"FANGSONG");
        a.handleRequest(request);
    }
}
