package com.model.demo.consumer;

import com.model.demo.message.DemoTopicMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 参考 http://www.iocoder.cn/Spring-Boot/RocketMQ/?self
 */

@Component
@RocketMQMessageListener(
        topic = DemoTopicMessage.TOPIC,
        consumerGroup = "demo01-consumer-group-" + DemoTopicMessage.TOPIC
)
public class Demo01Consumer implements RocketMQListener<DemoTopicMessage> {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(DemoTopicMessage message) {
        logger.info("Demo02Consumer 消费了一条消息");
        logger.info("Demo2 [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
