
package com.demo.flowable.dto;

import io.swagger.annotations.ApiModel;

import java.util.Map;

/**
 * 功能描述：接受或者拒绝
 *
 * @author lkz
 * @date 2022-03-08 23:49
 */

@ApiModel("接受拒绝入参")
public class AcceptOrRejectDTO {
    /**
     * 任务id
     */
    private String taskId;
    
    /**
     * 用户id
     */
    private String userId;
    
    /**
     * 流程中的变量
     */
    private Map<String, Object> map;
    
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Map<String, Object> getMap() {
        return map;
    }
    
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
