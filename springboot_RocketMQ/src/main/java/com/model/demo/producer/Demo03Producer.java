package com.model.demo.producer;


import com.model.demo.message.DemoTopicMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
  * @Description:  测试定时消息发送
  *         定时消息只支持 同步和异步
  * @Author:lkz
  * @Date:
  **/
 @Component
 @Configuration
public class Demo03Producer {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

     public SendResult syncSendDelay(Integer id, int delayLevel) {
         // 创建 Demo03Message 消息
         DemoTopicMessage mm = new DemoTopicMessage();
         mm.setId(id);
         mm.setMsg("发送同步的定时消息");
         Message message = MessageBuilder.withPayload(mm)
                 .build();
         // 同步发送消息
         System.out.println("发送时间：{}");
         return rocketMQTemplate.syncSend(DemoTopicMessage.TOPIC_03, message, 30 * 1000,
                 delayLevel);
     }

     public void asyncSendDelay(Integer id, int delayLevel, SendCallback callback) {
         // 创建 Demo03Message 消息
         DemoTopicMessage mm = new DemoTopicMessage();
         mm.setId(id);
         mm.setMsg("发送异步的定时消息");
         Message message = MessageBuilder.withPayload(mm)
                 .build();
         // 同步发送消息
         rocketMQTemplate.asyncSend(DemoTopicMessage.TOPIC_03, message, callback, 30 * 1000,
                 delayLevel);
     }
}
