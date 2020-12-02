package com.model.demo.demo;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class DemoProducerTest {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        //服务地址
        producer.setNamesrvAddr("129.211.164.41:9876");
        //实例化
        producer.start();
        for(int i=0;i<10;i++){
            //发送消息
            Message msg = new Message("TopicTest", "Tag","lkz",
                    ("Hello Rocker" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            //Call send message to deliver message to one of broker
            SendResult sendResult = producer.send(msg,10000);
            //打印发送结果
            System.out.println("发送结果："+sendResult);
        }
        //shut down once the producer instance in not longer in use;
        producer.shutdown();
    }

}
