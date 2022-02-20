package com.demo.chainofresponsibility.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Leader.java
 * @Description TODO 抽象类
 * @createTime 2022年02月19日 23:22:00
 */
public abstract class Leader {

    protected  String name;
    protected  Leader nextLeader; // 下一个节点 责任链的后继对象

    public Leader(String name) {
        this.name = name;
    }
    // 设置责任链上的后继对象
    public void setNextLeader(Leader nextLeader) {
        this.nextLeader = nextLeader;
    }



    //处理请求核心的业务方法
    public abstract  void handleRequest(LeaveRequest request);

}
