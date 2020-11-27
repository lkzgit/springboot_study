package com.model.demo.consumer;

import com.model.demo.message.DemoTopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = DemoTopicMessage.TOPIC_06,
        consumerGroup = "demo06-consumer-group-" + DemoTopicMessage.TOPIC_06,
        consumeMode = ConsumeMode.ORDERLY // 设置为顺序消费
)
public class Demo06Consumer implements RocketMQListener<DemoTopicMessage> {

    @Override
    public void onMessage(DemoTopicMessage message) {
        log.info("测试顺序消费");
        log.info("[onMessage][线程编号:{} 消息内容：{}]\n", Thread.currentThread().getId(), message);

        // sleep 2 秒，用于查看顺序消费的效果
        try {
            Thread.sleep(2 * 1000L);
        } catch (InterruptedException ignore) {
        }
    }


}
