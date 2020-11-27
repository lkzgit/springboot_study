package com.model.demo.producer;

import com.model.demo.message.DemoTopicMessage;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 测试广播
 * @Author:lkz
 * @Date:
 **/
@Component
public class Demo05Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSend(Integer id) {
        // 创建 Demo05Message 消息
        DemoTopicMessage message = new DemoTopicMessage();
        message.setMsg("广播测试同步");
        message.setId(id);
        // 同步发送消息
        return rocketMQTemplate.syncSend(DemoTopicMessage.TOPIC_05, message);
    }

}
