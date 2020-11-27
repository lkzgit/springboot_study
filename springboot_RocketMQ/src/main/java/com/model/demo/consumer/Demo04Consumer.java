package com.model.demo.consumer;




import com.model.demo.message.DemoTopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = DemoTopicMessage.TOPIC_04,
        consumerGroup = "demo04-consumer-group-" + DemoTopicMessage.TOPIC_04
)
@Slf4j
public class Demo04Consumer implements RocketMQListener<DemoTopicMessage> {


    @Override
    public void onMessage(DemoTopicMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]",Thread.currentThread().getId(),message);
        //抛出异常 模拟重复消费失败
        throw  new RuntimeException("这是一个故意的异常");
    }
}
