package com.model.demo.heima.itcast.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * sql过滤
 */
public class FilterConsumer {

    public static void main(String[] args) throws MQClientException {
       // #加入到broker的配置文件中 enablePropertyFilter=true
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("BatchProducer");
        consumer.setNamesrvAddr("129.211.164.41:9876");

        consumer.subscribe("Filteropic", MessageSelector.bySql("i>3"));
        //设置消息广播模式
        consumer.setMessageModel(MessageModel.CLUSTERING);//负载均衡模式
        //设置回调函数处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for(MessageExt msg:msgs){
                    try {
                        System.out.println("消息："+new String(msg.getBody(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
    }
}
