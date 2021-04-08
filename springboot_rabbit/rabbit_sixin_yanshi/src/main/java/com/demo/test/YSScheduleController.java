package com.demo.test;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列
 */
@Configuration
@EnableScheduling
@Slf4j
public class YSScheduleController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${exchange.name}")
    private String topicExchange;

    @Value("${delay.exchange.name}")
    private String delayTopicExchange;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendEmailMessage() {

        String msg = RandomStringUtils.randomAlphanumeric(8);
        JSONObject email=new JSONObject();
        email.put("content",msg);
        email.put("to","duchong@qq.com");
        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(topicExchange,"demo.email.x",email.toJSONString(),correlationData);
        log.info("---发送 email 消息---{}---messageId---{}",email,correlationData.getId());
    }


    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendDelayOrderMessage() throws Exception{

        //订单号 id实际是保存订单后返回的，这里用uuid代替
        String orderId = UUID.randomUUID().toString();
        // 模拟订单信息
        JSONObject order=new JSONObject();
        order.put("orderId",orderId);
        order.put("goodsName","vip充值");
        order.put("orderAmount","99.00");
        CorrelationData correlationData=new CorrelationData(orderId);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId(orderId);
        //30分钟时间太长，这里延时120s消费
        messageProperties.setHeader("x-delay", 120000);
        Message message = new Message(order.toJSONString().getBytes(CharEncoding.UTF_8), messageProperties);

        rabbitTemplate.convertAndSend(delayTopicExchange,"demo.delay.x",message,correlationData);

        log.info("---发送 order 消息---{}---orderId---{}",order,correlationData.getId());
        //睡一会，为了看延迟效果
        TimeUnit.MINUTES.sleep(10);
    }

}
