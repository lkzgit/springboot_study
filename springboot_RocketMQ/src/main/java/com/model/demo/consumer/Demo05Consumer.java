package com.model.demo.consumer;



import com.model.demo.message.DemoTopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
/**
 * @Description:  测试广播消费
 * @Author:lkz
 * @Date:
 **/
@Component
@RocketMQMessageListener(
        topic = DemoTopicMessage.TOPIC_05,
        consumerGroup = "demo05-consumer-group-" + DemoTopicMessage.TOPIC_05,
        messageModel = MessageModel.BROADCASTING // 设置为广播消费
)
@Slf4j
public class Demo05Consumer implements RocketMQListener<DemoTopicMessage> {


    @Override
    public void onMessage(DemoTopicMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
