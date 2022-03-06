
package com.demo.flowable.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：
 *
 * @author lkz
 * @date
 */
@Data
public class DoneEntity implements Serializable {
    /**
     * 任务id
     */
    private String taskId;
    
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 操作人姓名
     */
    private String approver;
    /**
     * 操作人id
     */
    private String approverId;
    /**
     * 表单名称
     */
    private String formName;
    /**
     * 业务id
     */
    private String businessKey;
    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     *
     */
    private String systemSn;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    
    @Override
    public String toString() {
        return "DoneEntity{" + "taskId='" + taskId + '\'' + ", taskName='" + taskName + '\'' + ", approver='" + approver
                + '\'' + ", approverId='" + approverId + '\'' + ", formName='" + formName + '\'' + ", businessKey='"
                + businessKey + '\'' + ", processInstanceId='" + processInstanceId + '\'' + ", systemSn='" + systemSn
                + '\'' + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
}
