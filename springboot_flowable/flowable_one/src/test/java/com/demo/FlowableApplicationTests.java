package com.demo;


import com.demo.flowable.entity.DoneEntity;
import com.demo.flowable.mapper.FlowableMapper;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class FlowableApplicationTests {
    /**
     * 部署
     */
    @Resource
    private RepositoryService repositoryService;
    
    /**
     * 启动
     */
    @Autowired
    private RuntimeService runtimeService;
    
    /**
     * 查询任务
     */
    @Autowired
    private TaskService taskService;
    
    @Resource
    private FlowableMapper flowableMapper;
    
    @Autowired
    private ProcessEngine processEngine;
    
    @Autowired
    private HttpServletResponse httpServletResponse;
    
    @Test
    void contextLoads() {
    }
    
    /**
     * 部署流程
     */
    @Test
    void createDeployment() {
        
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .addClasspathResource("bpmn/报销bpmn20.xml").name("OA报销流程");
        Deployment deploy = deploymentBuilder.deploy();
        System.out.println("流程部署成功");
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getKey());
        System.out.println(deploy.getParentDeploymentId());
    }
    
    /**
     * 启动流程
     */
    @Test
    void statusFlowable() {
        final String processId = "key-bx";
        Map<String, Object> map = new HashMap<>();
        map.put("hrUserId", "songxy");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processId, map);
        
        System.out.println(processInstance.getId());
    }
    
    /**
     * 查询部署的流程定义
     */
    @Test
    void findProcessDefinition() {
        //不分页
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("key-1")
                .list();
        list.forEach(processDefinition -> {
            System.out.println(processDefinition);
        });
        System.out.println("_________________");
        //分页
        List<ProcessDefinition> pages = repositoryService.createProcessDefinitionQuery().processDefinitionKey("key-1")
                .listPage(0, 10);
        pages.forEach(processDefinition -> {
            System.out.println(processDefinition);
        });
    }
    
    /**
     * 查询指定流程所有启动的实例列表
     */
    @Test
    void findExecutions() {
        //不分页
        List<Execution> executions = runtimeService.createExecutionQuery().processDefinitionKey("key-1").list();
        executions.forEach(execution -> {
            System.out.println(execution);
        });
        System.out.println("-----------");
        //分页
        List<Execution> executionPages = runtimeService.createExecutionQuery().processDefinitionKey("key-1")
                .listPage(0, 10);
        executionPages.forEach(execution -> {
            System.out.println(execution);
        });
    }
    
    /**
     * 查询当用户待办
     */
    @Test
    void findUserList() {
        String userId = "songxy";
        //不分页
        List<Task> list = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        list.forEach(task -> {
            System.out.println(task);
        });
        //分页
        List<Task> pages = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc()
                .listPage(0, 10);
        
    }
    
    /**
     * 审批任务  通过或者驳回
     */
    @Test
    void agreeOrRefuse() {
        Map<String, Object> map = new HashMap<>();
        map.put("financeUserId", "yqmm");
        map.put("money", "100");
        //        map.put("outcome", "驳回");
        //任务id
        String taskId = "55130c96-8429-11ec-8070-dc41a90b0909";
        String userId = "yqmm";
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //领取任务
        taskService.claim(task.getId(), userId);
        // 完成
        taskService.complete(task.getId(),map);
    }
    
    /**
     * 已办列表
     */
    @Test
    void done() {
        String userId = "songxy";
        List<DoneEntity> list = flowableMapper.doneByUserId(userId);
        list.forEach(doneEntity -> {
            System.out.println(doneEntity);
        });
    }
    
    /**
     * 生成流程图
     *
     *
     */
    @Test
    void genProcessDiagram() throws Exception {
        String processId = "e16b2b78-8406-11ec-b6f5-dc41a90b0909";
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        
        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(InstanceId).list();
        
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
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows,
                engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(),
                engconf.getClassLoader(), 1, true);
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
    
    @Test
    void reject() {
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("1f4594e9-83f6-11ec-9379-dc41a90b0909")
//                .moveActivityIdsToSingleActivityId("5ab4100f-83fc-11ec-95f0-dc41a90b0909","333")
                .changeState();
    }
}
