package com.demo.model.controller;

import com.demo.model.service.ProduceService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

@RestController
public class TestController {

    @Autowired
    private ProduceService produceService;

    @GetMapping("/order")
    public String test(){
        Destination destination=new ActiveMQQueue("order.queue");
        for(int i=1;i<=5;i++){
            produceService.sendMessage(destination,"指定队列"+i);
        }

        return "成功";
    }
    @GetMapping("/common")
    public String test2(){
       // Destination destination=new ActiveMQQueue("order.queue");
        produceService.sendMessage("common.queue队列");
        return "成功";
    }

    @GetMapping("/topic")
    public String test3(){
        produceService.publish("topic.topic主题订阅");
        return "成功";
    }
}
