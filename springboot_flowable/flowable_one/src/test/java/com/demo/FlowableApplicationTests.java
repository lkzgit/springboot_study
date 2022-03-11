package com.demo;


import com.demo.flowable.FlowableOneApplication;
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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowableOneApplication.class)
public class FlowableApplicationTests {
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
    

    
    /**
     * 部署流程
     */
    @Test
   public void createDeployment() {
        
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .addClasspathResource("bpmn/报销bpmn20.xml").name("OA报销流程");
        Deployment deploy = deploymentBuilder.deploy();
        System.out.println("流程部署成功");
        System.out.println("部署id："+deploy.getId());
        System.out.println("部署名称："+deploy.getName());
        System.out.println("部署key："+deploy.getKey());
        System.out.println("部署ParentDe："+deploy.getParentDeploymentId());
    }
    
    /**
     * 启动流程
     */
    @Test
    public void statusFlowable() {
        final String processKeyId = "key-bx"; //流程定义唯一的key
        Map<String, Object> map = new HashMap<>();
        map.put("hrUserId", "song");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKeyId, map);
        /**
         * 流程实例的id：43777d96-9df7-11ec-841d-f09e4a62ed63
         * 流程定义的id：key-bx:1:a1c8277a-9df5-11ec-9092-f09e4a62ed63
         * 流程定义的id：key-bx:1:a1c8277a-9df5-11ec-9092-f09e4a62ed63
         * 流程定义的定义key：key-bx
         */
        System.out.println("流程实例的id："+processInstance.getId());
        System.out.println("流程定义的id："+processInstance.getProcessDefinitionId());
        System.out.println("流程定义的定义key："+processInstance.getProcessDefinitionKey());
        System.out.println("流程实例当前活动节点节点："+processInstance.getActivityId());
    }
    /**
     * 根据流程定义id启动
     */
    @Test
    public void processInstanceId(){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("key-bx").singleResult();
        System.out.println("流程定义id:"+processDefinition.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("hrUserId", "song");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), map);
        System.out.println("流程实例的id："+processInstance.getId());
        System.out.println("流程定义的id："+processInstance.getProcessDefinitionId());
        System.out.println("流程定义的定义key："+processInstance.getProcessDefinitionKey());
        System.out.println("流程实例当前活动节点节点："+processInstance.getActivityId());
    }
    
    /**
     * 查询部署的流程定义
     */
    @Test
    public void findProcessDefinition() {
        //不分页
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("key-bx")
                .list();
        list.forEach(processDefinition -> {

            System.out.println("流程定义的id:"+processDefinition.getId());
            System.out.println("流程定义的名字:"+processDefinition.getName());
            System.out.println("流程定义的key:"+processDefinition.getKey());
        });
        System.out.println("_________________");
        //分页
        List<ProcessDefinition> pages = repositoryService.createProcessDefinitionQuery().processDefinitionKey("key-bx")
                .listPage(0, 10);
        pages.forEach(processDefinition -> {
            System.out.println(processDefinition);
        });
    }
    
    /**
     * 查询指定流程所有启动的实例列表
     */
    @Test
    public void findExecutions() {
        //不分页
        List<Execution> executions = runtimeService.createExecutionQuery().list();
        executions.forEach(execution -> {
            System.out.println(execution);
        });
        System.out.println("-----------");
        //分页
        List<Execution> executionPages = runtimeService.createExecutionQuery().processDefinitionKey("key-bx")
                .listPage(0, 10);
        executionPages.forEach(execution -> {
            System.out.println(execution);
        });
    }
    
    /**
     * 查询当用户待办
     */
    @Test
    public void findUserList() {
        String userId = "lisi";
        //不分页
        List<Task> list = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        list.forEach(task -> {
            System.out.println(task);
        });
        System.out.println("-------用户待办-------");
        //分页
        List<Task> pages = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc()
                .listPage(0, 10);
        
    }
    @Test
    public void lookweipai(){

        List<Task>list = taskService.createTaskQuery().taskOwner("zhangsan") //委托人名字

                .orderByTaskCreateTime().desc().list();

        list.forEach(e->{
            System.out.println("任务id:"+e.getId());
        });

    }
    /**
     * 转办
     */
    @Test
    public void turn(){
        //        根据key 和负责人来查询任务
        String taskId="e40af92a-9df7-11ec-a2d7-f09e4a62ed63";
        String assignee="zhangsan";
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskAssignee("song")
                .singleResult();
        if(task!=null){
            taskService.setAssignee(taskId,assignee);
        }
    }
    /**
     * 委托
     */
    @Test
    public void weituo(){
        //        根据key 和负责人来查询任务
        String taskId="e40af92a-9df7-11ec-a2d7-f09e4a62ed63";
        String assignee="lisi";
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskAssignee("zhangsan")
                .singleResult();
        if(task!=null){
            taskService.delegateTask(taskId, assignee);
        }
    }
    
    /**
     * 审批任务  通过或者驳回
     */
    @Test
    public void agreeOrRefuse() {
        Map<String, Object> map = new HashMap<>();
        map.put("financeUserId", "yqmm");
        map.put("money", "100");
        //        map.put("outcome", "驳回");
        //任务id
        String taskId = "e40af92a-9df7-11ec-a2d7-f09e4a62ed63";
        String userId = "yqmm";
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //领取任务
        taskService.claim(task.getId(), "lisi");
        // 完成
        taskService.complete(task.getId());
    }
    
    /**
     * 已办列表
     */
    @Test
    public void done() {
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
    public void genProcessDiagram() throws Exception {
        String processId = "43777d96-9df7-11ec-841d-f09e4a62ed63";
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
    public void reject() {
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("1f4594e9-83f6-11ec-9379-dc41a90b0909")
//                .moveActivityIdsToSingleActivityId("5ab4100f-83fc-11ec-95f0-dc41a90b0909","333")
                .changeState();
    }
}
