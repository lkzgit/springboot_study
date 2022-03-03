package com.demo.test;

import com.study.demo.ActivitiApplication;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lkz
 * @ClassName: Part4_Task
 * @description: TODO
 * @date 2022/3/3 19:14
 */
@SpringBootTest(classes = ActivitiApplication.class)
@RunWith(SpringRunner.class)
public class Part4_Task {


    @Autowired
    private TaskService taskService;

    //任务查询
    @org.junit.Test
    public void getTasks(){
        List<Task> list = taskService.createTaskQuery().list();
        for(Task tk : list){
            System.out.println("Id："+tk.getId());
            System.out.println("Name："+tk.getName());
            System.out.println("Assignee："+tk.getAssignee());
        }
    }

    //查询我的代办任务
    @org.junit.Test
    public void getTasksByAssignee(){
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("bajie")
                .list();
        for(Task tk : list){
            System.out.println("Id："+tk.getId());
            System.out.println("Name："+tk.getName());
            System.out.println("Assignee："+tk.getAssignee());
        }

    }

    //执行任务
    @org.junit.Test
    public void completeTask(){
        taskService.complete("d07d6026-cef8-11ea-a5f7-dcfb4875e032");
        System.out.println("完成任务");

    }

    //拾取任务
    @org.junit.Test
    public void claimTask(){
        Task task = taskService.createTaskQuery().taskId("1f2a8edf-cefa-11ea-84aa-dcfb4875e032").singleResult();
        taskService.claim("1f2a8edf-cefa-11ea-84aa-dcfb4875e032","bajie");
    }

    //归还与交办任务
    @org.junit.Test
    public void setTaskAssignee(){
        Task task = taskService.createTaskQuery().taskId("1f2a8edf-cefa-11ea-84aa-dcfb4875e032").singleResult();
        taskService.setAssignee("1f2a8edf-cefa-11ea-84aa-dcfb4875e032","null");//归还候选任务
        taskService.setAssignee("1f2a8edf-cefa-11ea-84aa-dcfb4875e032","wukong");//交办
    }
}
