package roy;


import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import roy.util.SecurityUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
public class  TestSecurityActiviti {
    private Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 查看流程定义内容
     * Activiti7可以自动部署流程
     * 跑不通，只能有一个bpmn，多了就不行。 看ProcessRuntimeImpl的selectLatestDeployment
     */
    @Test
    public void findProcess(){
        securityUtil.logInAs("jack");

        final ProcessDefinition processDefinition = processRuntime.processDefinition("mydemo");
        log.info("流程定义内容：{}",processDefinition);

        final Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        for (ProcessDefinition definition : processDefinitionPage.getContent()) {
            log.info("2- 流程定义内容:  {}",definition);
        }
    }

    /**
     * 启动流程
     * 报application version版本号不对。
     */
    @Test
    public void startProcess(){
//        设置登录用户
        securityUtil.logInAs("system");
        ProcessInstance processInstance = processRuntime.
                start(ProcessPayloadBuilder.
                        start().
                        withProcessDefinitionKey("mydemo").
                        build());
        log.info("流程实例的内容，{}",processInstance);
    }

    /**
     * 执行任务
     */
    @Test
    public void doTask(){
//        设置登录用户
        securityUtil.logInAs("jerry");
//        查询任务
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
        if(taskPage != null && taskPage.getTotalItems()>0){
            for (Task task : taskPage.getContent()) {
                //        拾取任务
                taskRuntime.claim(TaskPayloadBuilder.
                        claim().
                        withTaskId(task.getId()).
                        build());
                log.info("任务内容,{}",task);
                //        完成任务
                taskRuntime.complete(TaskPayloadBuilder.
                        complete().
                        withTaskId(task.getId()).
                        build());
            }
        }


    }
}
