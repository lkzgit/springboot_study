//package com.demo.test;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RandomStringUtils;
//
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//
///**
// * 死信队列消息发送方
// */
//
//@Slf4j
//@RequestMapping("/sixin")
//@RestController
//public class ScheduleController {
//
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//    @Value("${sixin.exchange.name}")
//    private String topicExchange;
//
//    @GetMapping("/sixinProducer")
//    public void sendEmailMessage() {
//
//        String msg = RandomStringUtils.randomAlphanumeric(8);
//        JSONObject email=new JSONObject();
//        email.put("content",msg);
//        email.put("to","duchong@qq.com");
//        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
//        rabbitTemplate.convertAndSend(topicExchange,"sixin-demo.email.x",email.toJSONString(),correlationData);
//        log.info("---发送 死信队列email 消息---{}---messageId---{}",email,correlationData.getId());
//    }
//
//
//}