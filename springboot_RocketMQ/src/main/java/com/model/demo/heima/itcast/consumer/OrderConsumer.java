package com.model.demo.heima.itcast.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Description: 测试顺序消费
 * @Author:lkz
 * @Date:
 **/
public class OrderConsumer {

    public static void main(String[] args) {
        try{
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("HAOKE_ORDER_CONSUMER");
            consumer.setNamesrvAddr("129.211.164.41:9876");
            consumer.subscribe("haoke_order_topic", "*");
            consumer.registerMessageListener(new MessageListenerOrderly() { // 有序的消费Orderly
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                           ConsumeOrderlyContext context) {
                    for(MessageExt msg:msgs){
                        try {
                            System.out.println(Thread.currentThread().getName() + " Receive New Messages: "
                            +" "+msg.getQueueId()+" "+new String(msg.getBody(),"UTF-8"));
                        }catch (Exception e){

                        }
                    }

                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
        /**
         *测试结果：相同订单id的消息会落到同一个queue中，
         * 一个消费者线程会顺序消费queue，从而实现顺序消费消
         * 息。
         **/
        }catch (Exception e){

        }

    }
}
