
package com.demo.flowable.dto;

import io.swagger.annotations.ApiModel;

/**
 * 功能描述：部署对象
 *
 * @author lkz
 * @date 2022-03-08 23:01
 */

@ApiModel("部署入参")

public class DeploymentDTO {
    /**
     * 部署的流程名称
     */
    private String deploymentFlowName;
    
    /**
     * resource文件路径
     */
    private String resourceBpmnPath;
    
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
    
    public String getDeploymentFlowName() {
        return deploymentFlowName;
    }
    
    public void setDeploymentFlowName(String deploymentFlowName) {
        this.deploymentFlowName = deploymentFlowName;
    }
    
    public String getResourceBpmnPath() {
        return resourceBpmnPath;
    }
    
    public void setResourceBpmnPath(String resourceBpmnPath) {
        this.resourceBpmnPath = resourceBpmnPath;
    }
    
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
}
