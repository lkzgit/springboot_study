
package com.demo.flowable.service.impl;


import com.demo.flowable.dto.*;
import com.demo.flowable.entity.DoneEntity;
import com.demo.flowable.mapper.FlowableMapper;
import com.demo.flowable.service.FlowableService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 功能描述：
 *
 * @author lkz
 * @date
 */
@Service
public class FlowableServiceImpl implements FlowableService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    
    @Resource
    private FlowableMapper flowableMapper;
    
    @Override
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
    
        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();
    
        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
    
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0,true);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
    
    @Override
    public String createDeployment(DeploymentDTO dto) {
        Deployment deploy = repositoryService.createDeployment().addClasspathResource(dto.getResourceBpmnPath())
                .name(dto.getDeploymentFlowName()).deploy();
        return "流程部署成功,部署的实例id为："+deploy.getId();
    }
    
    @Override
    public List<ProcessDefinition> getProcessDefinitionList(DeploymentDTO dto) {
        if (Objects.nonNull(dto.getPage()) || Objects.nonNull(dto.getPageSize())) {
            List<ProcessDefinition> pages = repositoryService.createProcessDefinitionQuery().processDefinitionKey(dto.getProcessDefinitionKey())
                    .listPage(dto.getPage(), dto.getPageSize());
            return pages;
        }
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(dto.getProcessDefinitionKey())
                .list();
        return list;
    }
    
    @Override
    public String startFlowable(StartFlowableDTO dto) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(dto.getProcessDefinitionKey(),
                dto.getMap());
        return "启动流程成功，流程实例ID为：" + processInstance.getId();
    }
    
    @Override
    public List<Execution> executions(StartFlowableDTO dto) {
        //分页
        if (Objects.nonNull(dto.getPage()) || Objects.nonNull(dto.getPageSize())) {
            List<Execution> executionPages = runtimeService.createExecutionQuery().processDefinitionKey(dto.getProcessDefinitionKey())
                    .listPage(dto.getPage(), dto.getPageSize());
            return executionPages;
        }
        //不分页
        List<Execution> executions = runtimeService.createExecutionQuery().processDefinitionKey(dto.getProcessDefinitionKey()).list();
        return executions;
    }
    
    @Override
    public List<Task> todoList(TodoDTO dto) {
        if (Objects.nonNull(dto.getPage()) || Objects.nonNull(dto.getPageSize())) {
            //分页
            List<Task> pages = taskService.createTaskQuery().taskAssignee(dto.getUserId()).orderByTaskCreateTime().desc()
                    .listPage(dto.getPage(), dto.getPageSize());
            return pages;
        }
        //不分页
        List<Task> list = taskService.createTaskQuery().taskAssignee(dto.getUserId()).orderByTaskCreateTime().desc().list();
    
        return list;
    }
    
    @Override
    public List<DoneEntity> doneList(DoneDTO dto) {
        List<DoneEntity> list = flowableMapper.doneByUserId(dto.getUserId());
        return list;
    }
    
    @Override
    public String acceptOrReject(AcceptOrRejectDTO dto) {
        Task task = taskService.createTaskQuery().taskId(dto.getTaskId()).singleResult();
        //领取任务
        taskService.claim(task.getId(), dto.getUserId());
        // 完成
        taskService.complete(task.getId(),dto.getMap());
        return "流程执行成功！";
    }
    
    @Override
    public List<Task> tasks(String group) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();
        return tasks;
    }
    
    @Override
    public List<HistoricActivityInstance> historicActivityInstances(String processInstanceId) {
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).finished().orderByHistoricActivityInstanceEndTime().asc().list();
        return historicActivityInstances;
    }
    
    @Override
    public String  currentTask(String taskId, String targetTaskKey) {
        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (currentTask == null) {
            return "节点不存在";
        }
        List<String> key = new ArrayList<>();
        key.add(currentTask.getTaskDefinitionKey());
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(currentTask.getProcessInstanceId())
                .moveActivityIdsToSingleActivityId(key, targetTaskKey)
                .changeState();
        return "驳回成功...";
    }
    
    @Override
    public String deleteProcessInstanceById(String processInstanceId,String reason) {
        runtimeService.deleteProcessInstance(processInstanceId, reason);
        return "终止流程实例成功";
    }
    
    @Override
    public String handUpProcessInstance(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return "挂起流程成功...";
    }
    
    @Override
    public String activateProcessInstance(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
        return "恢复流程成功...";
    }
    
    @Override
    public Boolean isExistProcIntRunning(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            return false;
        }
        return true;
    }
    
    @Override
    public List<HistoricProcessInstance> getMyStartProcint(String userId) {
        List<HistoricProcessInstance> list = historyService
                .createHistoricProcessInstanceQuery()
                .startedBy(userId)
                .orderByProcessInstanceStartTime()
                .asc()
                .list();
        return list;
    }
    
   
    
}
