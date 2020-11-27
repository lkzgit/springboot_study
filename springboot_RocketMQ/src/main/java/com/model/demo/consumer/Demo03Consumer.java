package com.model.demo.consumer;

import com.model.demo.message.DemoTopicMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RocketMQMessageListener(
        topic = DemoTopicMessage.TOPIC_03,
        consumerGroup = "demo03-consumer-group-" + DemoTopicMessage.TOPIC_03
)
public class Demo03Consumer implements RocketMQListener<DemoTopicMessage> {



        private Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void onMessage(DemoTopicMessage message) {
            logger.info("接收到定时消息:"+new Date());
            logger.info("[onMessage][线程编号:{} 消息内容：{}]:\n", Thread.currentThread().getId(), message);
        }


}
