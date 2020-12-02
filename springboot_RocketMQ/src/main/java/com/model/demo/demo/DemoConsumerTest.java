package com.model.demo.demo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class DemoConsumerTest {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        /*
         * Instantiate with specified consumer group name.
         */
        // <1> 创建 DefaultMQPushConsumer 对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
        // <2> 设置 RocketMQ Namesrv 地址
        consumer.setNamesrvAddr("129.211.164.41:9876");


        // <3> 设置消费进度，从 Topic 最初位置开始
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        /*
         * Subscribe one more more topics to consume.
         */
        // <4> 订阅 TopicTest 主题
        consumer.subscribe("TopicTest", "Tag");

        /*
         *  Register callback to execute on arrival of messages fetched from brokers.
         */
        // <5> 添加消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs.toString());
                // 返回成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

        });

        /*
         *  Launch the consumer instance.
         */
        // <6> 启动 producer 消费者
        consumer.start();

        // 打印 Consumer 启动完成
        System.out.printf("Consumer Started.%n");
    }

}
