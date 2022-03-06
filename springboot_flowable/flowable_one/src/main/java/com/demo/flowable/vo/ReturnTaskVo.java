
package com.demo.flowable.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述：Task居然不是个实体，是一个接口。我们需要封装起来
 *
 * @author lkz
 * @date
 */
@ApiModel("返回Task")
public class ReturnTaskVo {
    @ApiModelProperty("任务id")
    private String taskId;
    
    @ApiModelProperty("父任务id")
    private String parentTaskId;
    
    @ApiModelProperty("任务名称")
    private String name;
    
    @ApiModelProperty("流程实例id")
    private String processInstanceId;
    
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getParentTaskId() {
        return parentTaskId;
    }
    
    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getProcessInstanceId() {
        return processInstanceId;
    }
    
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
