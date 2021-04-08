package com.demo.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;


/**
 * 死信队列消息发送方
 */
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${exchange.name}")
    private String topicExchange;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void sendEmailMessage() {

        String msg = RandomStringUtils.randomAlphanumeric(8);
        JSONObject email=new JSONObject();
        email.put("content",msg);
        email.put("to","duchong@qq.com");
        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(topicExchange,"demo.email.x",email.toJSONString(),correlationData);
        log.info("---发送 email 消息---{}---messageId---{}",email,correlationData.getId());
    }


}