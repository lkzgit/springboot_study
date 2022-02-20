package com.demo.chainofresponsibility.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName LeaveRequest.java
 * @Description TODO 请求的基本信息
 * @createTime 2022年02月19日 23:20:00
 */
public class LeaveRequest {

    private String empName;
    private int leaveDay;
    private String reason;

    public LeaveRequest(String empName, int leaveDay, String reason) {
        this.empName = empName;
        this.leaveDay = leaveDay;
        this.reason = reason;
    }

    public LeaveRequest() {
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(int leaveDay) {
        this.leaveDay = leaveDay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
