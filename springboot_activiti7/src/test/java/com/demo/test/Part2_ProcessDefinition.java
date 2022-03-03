package com.demo.test;

import com.study.demo.ActivitiApplication;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

/**
 * @author lkz
 * @ClassName: Part2_ProcessDefinition
 * @description: TODO
 * @date 2022/3/3 18:57
 */

@SpringBootTest(classes = ActivitiApplication.class)
@RunWith(SpringRunner.class)
public class Part2_ProcessDefinition {

    @Autowired
    private RepositoryService repositoryService;

    //查询流程定义
    @org.junit.Test
    public void getDefinitions(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();
        for(ProcessDefinition pd : list){
            System.out.println("------流程定义--------");
            System.out.println("Name："+pd.getName());
            System.out.println("Key："+pd.getKey());
            System.out.println("ResourceName："+pd.getResourceName());
            System.out.println("DeploymentId："+pd.getDeploymentId());
            System.out.println("Version："+pd.getVersion());

        }

    }

    //删除流程定义
    @org.junit.Test
    public void delDefinition(){

        String pdID="44b15cfe-ce3e-11ea-92a3-dcfb4875e032";
        repositoryService.deleteDeployment(pdID,true);
        System.out.println("删除流程定义成功");
    }
}
