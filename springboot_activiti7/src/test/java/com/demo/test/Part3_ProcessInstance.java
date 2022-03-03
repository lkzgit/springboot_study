package com.demo.test;

import com.study.demo.ActivitiApplication;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lkz
 * @ClassName: Part3_ProcessInstance
 * @description: TODO
 * @date 2022/3/3 18:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiApplication.class)
public class Part3_ProcessInstance {


    @Autowired
    private RuntimeService runtimeService;

    //��ʼ������ʵ��
    @org.junit.Test
    public void initProcessInstance(){
        //1����ȡҳ���������ݣ����ʱ�䣬������ɣ�String fromData
        //2��fromData д��ҵ�������ҵ�������ID==businessKey
        //3����ҵ��������Activiti7�������ݹ���
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_claim","bKey002");
        System.out.println("����ʵ��ID��"+processInstance.getProcessDefinitionId());

    }

    //��ȡ����ʵ���б�
    @org.junit.Test
    public void getProcessInstances(){
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        for(ProcessInstance pi : list){
            System.out.println("--------����ʵ��------");
            System.out.println("ProcessInstanceId��"+pi.getProcessInstanceId());
            System.out.println("ProcessDefinitionId��"+pi.getProcessDefinitionId());
            System.out.println("isEnded"+pi.isEnded());
            System.out.println("isSuspended��"+pi.isSuspended());

        }

    }


    //��ͣ�뼤������ʵ��
    @org.junit.Test
    public void activitieProcessInstance(){
        // runtimeService.suspendProcessInstanceById("73f0fb9a-ce5b-11ea-bf67-dcfb4875e032");
        //System.out.println("��������ʵ��");

        runtimeService.activateProcessInstanceById("73f0fb9a-ce5b-11ea-bf67-dcfb4875e032");
        System.out.println("��������ʵ��");
    }

    //ɾ������ʵ��
    @org.junit.Test
    public void delProcessInstance(){
        runtimeService.deleteProcessInstance("73f0fb9a-ce5b-11ea-bf67-dcfb4875e032","ɾ����");
        System.out.println("ɾ������ʵ��");
    }

}
