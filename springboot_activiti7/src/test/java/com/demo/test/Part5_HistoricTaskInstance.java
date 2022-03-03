package com.demo.test;

import com.study.demo.ActivitiApplication;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lkz
 * @ClassName: Part5_HistoricTaskInstance
 * @description: 历史任务
 * @date 2022/3/3 19:15
 */
@SpringBootTest(classes = ActivitiApplication.class)
@RunWith(SpringRunner.class)
public class Part5_HistoricTaskInstance {

    @Autowired
    private HistoryService historyService;

    //根据用户名查询历史记录
    @org.junit.Test
    public void HistoricTaskInstanceByUser(){
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().asc()
                .taskAssignee("bajie")
                .list();
        for(HistoricTaskInstance hi : list){
            System.out.println("Id："+ hi.getId());
            System.out.println("ProcessInstanceId："+ hi.getProcessInstanceId());
            System.out.println("Name："+ hi.getName());

        }

    }


    //根据流程实例ID查询历史
    @org.junit.Test
    public void HistoricTaskInstanceByPiID(){
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().asc()
                .processInstanceId("1f2314cb-cefa-11ea-84aa-dcfb4875e032")
                .list();
        for(HistoricTaskInstance hi : list){
            System.out.println("Id："+ hi.getId());
            System.out.println("ProcessInstanceId："+ hi.getProcessInstanceId());
            System.out.println("Name："+ hi.getName());

        }
    }
}
