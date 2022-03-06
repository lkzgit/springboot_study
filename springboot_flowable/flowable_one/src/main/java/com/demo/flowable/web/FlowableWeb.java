
package com.demo.flowable.web;


import com.demo.flowable.dto.*;
import com.demo.flowable.entity.DoneEntity;
import com.demo.flowable.service.FlowableService;
import com.demo.flowable.utils.TaskConvert;
import com.demo.flowable.vo.ReturnTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 功能描述：
 *
 * @author lkz
 * @date
 */
@Api(tags = "工作流接口")
@RestController
@RequestMapping("flow")
@Slf4j

public class FlowableWeb {
    @Resource
    private FlowableService flowableService;
    
    @GetMapping("get-flow-chart")
    @ApiOperation("流程图")
    public void getFlowChart( HttpServletResponse response, String processId) throws Exception {
        flowableService.genProcessDiagram(response,processId);
    }
    
    /**
     * 流程部署
     * @param dto 流程部署对象
     * @return
     */
    @ApiOperation("流程部署")
    @PostMapping("create-deployment")
    public String createDeployment(@RequestBody DeploymentDTO dto) {
        return flowableService.createDeployment(dto);
    }
    
    /**
     * 流程部署 列表
     * @param dto
     * @return
     */
    @ApiOperation("流程部署 列表")
    @PostMapping("get-process-definition-list")
    List<ProcessDefinition> getProcessDefinitionList(@RequestBody DeploymentDTO dto) {
        return flowableService.getProcessDefinitionList(dto);
    }
    
    /**
     * 启动流程
     * @param dto  启动流程参数
     * @return
     */
    @ApiOperation("启动流程")
    @PostMapping("start-flowable")
    String startFlowable(@RequestBody StartFlowableDTO dto){
        return flowableService.startFlowable(dto);
    }
    
    /**
     * 查询所有启动的流程列表
     * @param dto
     * @return
     */
    @ApiOperation("查询所有启动的流程列表")
    @PostMapping("executions")
    List<Execution> executions(@RequestBody StartFlowableDTO dto) {
        return flowableService.executions(dto);
    }
    
    
    /**
     * 用户待办
     * @param dto
     * @return
     */
    @ApiOperation("用户待办")
    @PostMapping("todo-list")
    List<ReturnTaskVo> todoList(@RequestBody TodoDTO dto) {
        return TaskConvert.INSTANCE.toConvertTaskVoList(flowableService.todoList(dto));
    }
    /**
     * 用户已办
     * @param dto
     * @return
     */
    @ApiOperation("用户已办")
    @PostMapping("done-list")
    List<DoneEntity> doneList(@RequestBody DoneDTO dto) {
        return flowableService.doneList(dto);
    }
    
    /**
     * 通过或者拒绝
     * @param dto
     * @return
     */
    @ApiOperation("通过或者拒绝")
    @PostMapping("accept-or-reject")
    String acceptOrReject(@RequestBody AcceptOrRejectDTO dto) {
        return flowableService.acceptOrReject(dto);
    }
    
    /**
     * 获取指定用户组流程任务列表
     * @param group
     * @return List<Task>
     */
    @ApiOperation("获取指定用户组流程任务列表")
    @GetMapping("tasks-group/{group}")
    List<ReturnTaskVo>  tasks(@PathVariable("group") String group) {
        return TaskConvert.INSTANCE.toConvertTaskVoList(flowableService.tasks(group));
    }
    
    /**
     *查看历史流程记录
     * @param processInstanceId
     * @return List
     */
    @ApiOperation("查看历史流程记录")
    @GetMapping("historic-activity-instances/{processInstanceId}")
    List<HistoricActivityInstance> historicActivityInstances(@PathVariable String processInstanceId) {
        return flowableService.historicActivityInstances(processInstanceId);
    }
    /**
     * 驳回流程
     * @param targetTaskKey
     * @param taskId
     * @return Task
     */
    @ApiOperation("驳回流程")
    @GetMapping("current-task/{taskId}/{targetTaskKey}")
    String  currentTask(@PathVariable String taskId, @PathVariable String targetTaskKey){
        return flowableService.currentTask(taskId, targetTaskKey);
    }
    
    /**
     *终止流程
     * @param processInstanceId
     * @param reason 原因
     * @return string
     */
    @ApiOperation("终止流程")
    @GetMapping("delete-process-instance-by-id/{processInstanceId}/{reason}")
    String deleteProcessInstanceById(@PathVariable String processInstanceId,@PathVariable String reason) {
        return flowableService.deleteProcessInstanceById(processInstanceId, reason);
    }
    /**
     *挂起流程实例
     * @param processInstanceId
     * @return string
     */
    @ApiOperation("挂起流程实例")
    @GetMapping("hand-up-process-instance/{processInstanceId}")
    String handUpProcessInstance(@PathVariable String processInstanceId){
        return flowableService.handUpProcessInstance(processInstanceId);
    }
    /**
     *（唤醒）被挂起的流程实例
     * @param processInstanceId
     * @return string
     */
    @ApiOperation("（唤醒）被挂起的流程实例")
    @GetMapping("activate-process-instance/{processInstanceId}")
    String activateProcessInstance(@PathVariable String processInstanceId){
        return flowableService.activateProcessInstance(processInstanceId);
    }
    /**
     *判断传入流程实例在运行中是否存在
     * @param processInstanceId
     * @return 布尔
     */
    @ApiOperation("判断传入流程实例在运行中是否存在")
    @GetMapping("is-exist-proc-int-running/{processInstanceId}")
    Boolean isExistProcIntRunning(@PathVariable String processInstanceId) {
        return flowableService.isExistProcIntRunning(processInstanceId);
    }
    /**
     *我发起的流程实例列表
     * @param userId
     * @return list
     */
    @ApiOperation("我发起的流程实例列表")
    @GetMapping("get-my-start-procint/{userId}")
    List<HistoricProcessInstance> getMyStartProcint(@PathVariable String userId) {
        return flowableService.getMyStartProcint(userId);
    }
    
}
