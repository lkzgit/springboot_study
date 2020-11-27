package com.model.demo.heima.itcast.topic;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/*
 * @Description:  创建topic
 * @Author:lkz
 * @Date:
 **/
public class TopicDemo {

    public static void main(String[] args) {

        try{
            DefaultMQProducer produce = new DefaultMQProducer("haoke_im");
            produce.setNamesrvAddr("129.211.164.41:9876");
            produce.start();
            /**
             * key：broker名称
             * newTopic：topic名称
             * queueNum：队列数（分区）
             **/
            produce.createTopic("broker_haoke_im","haoke_im_topic",8);
            System.out.println("创建成功");
            produce.shutdown();
        }catch (Exception e){

        }

    }

}
