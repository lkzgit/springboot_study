
package com.demo.flowable.service;


import com.demo.flowable.dto.*;
import com.demo.flowable.entity.DoneEntity;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.task.api.Task;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 功能描述：
 *
 * @author lkz
 * @date
 */
public interface FlowableService {
    /**
     * 查看流程图
     * @param httpServletResponse 相应
     * @param processId  流程实例id
     */
    void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception;
    
    /**
     * 流程部署
     * @param dto 流程部署对象
     * @return
     */
    String createDeployment(DeploymentDTO dto);
    
    /**
     * 流程部署 列表
     * @param dto
     * @return
     */
    List<ProcessDefinition> getProcessDefinitionList(DeploymentDTO dto);
    
    /**
     * 启动流程
     * @param dto  启动流程参数
     * @return
     */
    String startFlowable(StartFlowableDTO dto);
    
    /**
     * 查询所有启动的流程列表
     * @param dto
     * @return
     */
    List<Execution> executions(StartFlowableDTO dto);
    
    /**
     * 用户待办
     * @param dto
     * @return
     */
    List<Task> todoList(TodoDTO dto);
    /**
     * 用户已办
     * @param dto
     * @return
     */
    List<DoneEntity> doneList(DoneDTO dto);
    
    /**
     * 通过或者拒绝
     * @param dto
     * @return
     */
    String acceptOrReject(AcceptOrRejectDTO dto);
    
    /**
     * 获取指定用户组流程任务列表
     * @param group
     * @return List<Task>
     */
    List<Task> tasks(String group);
    
    /**
     *查看历史流程记录
     * @param processInstanceId
     * @return List
     */
    List<HistoricActivityInstance> historicActivityInstances(String processInstanceId);
    /**
     * 驳回流程
     * @param targetTaskKey
     * @param taskId
     * @return Task
     */
    String  currentTask(String taskId, String targetTaskKey);
    
    /**
     *终止流程
     * @param processInstanceId
     * @param reason 原因
     * @return string
     */
    String deleteProcessInstanceById(String processInstanceId,String reason);
    /**
     *挂起流程实例
     * @param processInstanceId
     * @return string
     */
    String handUpProcessInstance(String processInstanceId);
    /**
     *（唤醒）被挂起的流程实例
     * @param processInstanceId
     * @return string
     */
    String activateProcessInstance(String processInstanceId);
     /**
     *判断传入流程实例在运行中是否存在
      * @param processInstanceId
      * @return 布尔
     */
     Boolean isExistProcIntRunning(String processInstanceId);
     /**
     *我发起的流程实例列表
      * @param userId
      * @return list
     */
     List<HistoricProcessInstance> getMyStartProcint(String userId);
     
    
}
