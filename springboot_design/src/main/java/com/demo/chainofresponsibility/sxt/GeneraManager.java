package com.demo.chainofresponsibility.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Director.java
 * @Description TODO 总经理
 * @createTime 2022年02月19日 23:29:00
 */
public class GeneraManager extends Leader{


    // 继承父类中的名字
    public GeneraManager(String name) {
        super(name);
    }

    @Override
    public void handleRequest(LeaveRequest request) {
        if(request.getLeaveDay()<30){
            System.out.println("员工："+request.getEmpName()+"请假");
            System.out.println("总经理："+name+"审批通过");
        }else{
            System.out.println("该员工想辞职");
        }
    }
}
