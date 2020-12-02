package com.model.demo.heima.itcast.producer;


import org.apache.rocketmq.client.producer.DefaultMQProducer;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;


public class BatchProducer {

    public static void main(String[] args) {
        try{
            DefaultMQProducer pro = new DefaultMQProducer("BatchProducer");
            pro.setNamesrvAddr("129.211.164.41:9876");
            pro.start();

            List<Message> msgs=new ArrayList<>();
            Message m1=new Message("Batchtopic","send_msg",("Hello_word"+1).getBytes());
            Message m2=new Message("Batchtopic","send_msg",("Hello_word"+2).getBytes());
            Message m3=new Message("Batchtopic","send_msg",("Hello_word"+3).getBytes());
            msgs.add(m1);
            msgs.add(m2);
            msgs.add(m3);
            SendResult send = pro.send(msgs);
            System.out.println("发送结果"+send);
            pro.shutdown();

        }catch (Exception e){

        }


    }

}
