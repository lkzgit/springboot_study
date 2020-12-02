package com.model.demo.heima.itcast.consumer;



import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;

import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Description:  测试消息消费
 * @Author:lkz
 * @Date:
 **/
public class ConsumerDemo {

    public static void main(String[] args) {
        try {
            // 客户端想服务端推送消息
            DefaultMQPushConsumer con = new DefaultMQPushConsumer("haoke_consumer");
            con.setNamesrvAddr("129.211.164.41:9876");

           /**
            完整匹配 consumer.subscribe("haoke_im_topic", "SEND_MSG");
           或匹配 consumer.subscribe("haoke_im_topic", "SEND_MSG || SEND_MSG1");
            **/
           //设置监听器
            con.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    for(MessageExt msg:msgs){
                        try {
                            System.out.println("消息："+new String(msg.getBody(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("收到消息->" + msgs);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            con.start();

        }catch (Exception e){

        }

    }

}
