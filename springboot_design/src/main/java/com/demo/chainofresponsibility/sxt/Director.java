package com.demo.chainofresponsibility.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Director.java
 * @Description TODO 主任
 * @createTime 2022年02月19日 23:29:00
 */
public class Director extends Leader{


    // 继承父类中的名字
    public Director(String name) {
        super(name);
    }

    @Override
    public void handleRequest(LeaveRequest request) {
        if(request.getLeaveDay()<3){
            System.out.println("员工："+request.getEmpName()+"请假");
            System.out.println("主任："+name+"审批通过");
        }else{
            // 下个节点不为空时才传递
            if(this.nextLeader!=null){
                this.nextLeader.handleRequest(request);
            }
        }
    }
}
