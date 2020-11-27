package com.model.demo.consumer;

import com.model.demo.message.DemoTopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = DemoTopicMessage.TOPIC_07,
        consumerGroup = "demo07-consumer-group-" + DemoTopicMessage.TOPIC_07
)
@Slf4j
public class Demo07Consumer implements RocketMQListener<DemoTopicMessage> {


    @Override
    public void onMessage(DemoTopicMessage message) {
        log.info("事务消息:{}");
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
