
package com.demo.flowable.dto;

import io.swagger.annotations.ApiModel;

import java.util.Map;

/**
 * 功能描述：
 *
 * @author lkz
 * @date 2022-03-08 23:28
 */
@ApiModel("启动流程入参")
public class StartFlowableDTO {
    /**
     * 查询部署的流程列表-流程key
     */
    private String processDefinitionKey;
    /**
     * 那一页开始
     */
    private Integer page;
    
    /**
     * 每页显示条数
     */
    private Integer pageSize;
    
    /**
     * 流程变量
     */
    private Map<String, Object> map;
    
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }
    
    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }
    
    public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public Map<String, Object> getMap() {
        return map;
    }
    
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
