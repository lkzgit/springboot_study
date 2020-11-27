package com.model.demo.consumer;


import com.model.demo.message.DemoTopicMessage;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = DemoTopicMessage.TOPIC,
        consumerGroup = "demo01-A-consumer-group-" + DemoTopicMessage.TOPIC
)
public class Demo01AConsumer implements RocketMQListener<MessageExt> {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(MessageExt message) {
        logger.info("Demo01AConsumer 消费了一条消息");
        logger.info("Demo1 [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
