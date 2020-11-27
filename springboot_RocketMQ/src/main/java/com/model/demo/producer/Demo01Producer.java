package com.model.demo.producer;


import com.model.demo.message.DemoTopicMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *   参考 http://www.iocoder.cn/Spring-Boot/RocketMQ/?self
 *
 * 它会使用 RocketMQ-Spring 封装提供的 RocketMQTemplate ，实现三种发送消息的方式
 */
@Component
public class Demo01Producer {


    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSend(Integer id) {
        System.out.println("发送同步消息----");
        // 创建 Demo01Message 消息
        DemoTopicMessage message = new DemoTopicMessage();
        message.setId(id);
        // 同步发送消息
        return rocketMQTemplate.syncSend(DemoTopicMessage.TOPIC, message);
    }

    public void asyncSend(Integer id, SendCallback callback) {
        System.out.println("发送异步消息");
        // 创建 Demo01Message 消息
        DemoTopicMessage message = new DemoTopicMessage();
        message.setId(id);
        // 异步发送消息
        rocketMQTemplate.asyncSend(DemoTopicMessage.TOPIC, message, callback);
    }

    public void onewaySend(Integer id) {
        System.out.println("发送消息");
        // 创建 Demo01Message 消息
        DemoTopicMessage message = new DemoTopicMessage();
        message.setId(id);
        // oneway 发送消息
        rocketMQTemplate.sendOneWay(DemoTopicMessage.TOPIC, message);
    }

}
