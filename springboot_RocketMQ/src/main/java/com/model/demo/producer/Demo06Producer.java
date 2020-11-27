package com.model.demo.producer;

import com.model.demo.message.DemoTopicMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:  顺序消费
 * @Author:lkz     调用了对应的 Orderly 方法，从而实现发送顺序消息
 * @Date:  2020-11-27
 *
 **/
@Component
public class Demo06Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    //测试同步
    public SendResult syncSendOrderly(Integer id) {
        // 创建 Demo06Message 消息
        DemoTopicMessage message = new DemoTopicMessage();
        message.setMsg("demo6同步顺序消费");
        message.setId(id);
        // 同步发送消息
        return rocketMQTemplate.syncSendOrderly(DemoTopicMessage.TOPIC_06, message, String.valueOf(id));
    }
    //测试异步
    public void asyncSendOrderly(Integer id, SendCallback callback) {
        // 创建 Demo06Message 消息
        DemoTopicMessage message = new DemoTopicMessage();
        message.setMsg("demo6异步顺序消息");
        message.setId(id);
        // 异步发送消息
        rocketMQTemplate.asyncSendOrderly(DemoTopicMessage.TOPIC_06, message, String.valueOf(id), callback);
    }
    //直接发送
    public void onewaySendOrderly(Integer id) {
        // 创建 Demo06Message 消息
        DemoTopicMessage message = new DemoTopicMessage();
        message.setMsg("OneWay顺序消息");
        message.setId(id);
        // 异步发送消息
        rocketMQTemplate.sendOneWayOrderly(DemoTopicMessage.TOPIC_06, message, String.valueOf(id));
    }

}
