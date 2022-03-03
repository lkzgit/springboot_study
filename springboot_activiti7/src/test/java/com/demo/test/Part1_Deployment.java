package com.demo.test;

import com.study.demo.ActivitiApplication;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author lkz
 * @ClassName: Part1_Deployment
 * @description: 流程部署
 * @date 2022/3/3 19:11
 */
@SpringBootTest(classes = ActivitiApplication.class)
@RunWith(SpringRunner.class)
public class Part1_Deployment {

    @Autowired
    private RepositoryService repositoryService;

    //通过bpmn部署流程
    @org.junit.Test
    public void initDeploymentBPMN(){
        String filename="BPMN/Part4_Task_claim.bpmn";
        // String pngname="BPMN/Part1_Deployment.png";
        Deployment deployment=repositoryService.createDeployment()
                .addClasspathResource(filename)
                //.addClasspathResource(pngname)//图片
                .name("流程部署测试候选人task")
                .deploy();
        System.out.println(deployment.getName());
    }

    //通过ZIP部署流程
    @org.junit.Test
    public void initDeploymentZIP() {
        InputStream fileInputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("BPMN/Part1_DeploymentV2.zip");
        ZipInputStream zip=new ZipInputStream(fileInputStream);
        Deployment deployment=repositoryService.createDeployment()
                .addZipInputStream(zip)
                .name("流程部署测试zip")
                .deploy();
        System.out.println(deployment.getName());
    }

    //查询流程部署
    @org.junit.Test
    public void getDeployments() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        for(Deployment dep : list){
            System.out.println("Id："+dep.getId());
            System.out.println("Name："+dep.getName());
            System.out.println("DeploymentTime："+dep.getDeploymentTime());
            System.out.println("Key："+dep.getKey());
        }

    }

}
