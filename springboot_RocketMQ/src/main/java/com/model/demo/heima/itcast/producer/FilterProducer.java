package com.model.demo.heima.itcast.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * sql过滤
 */
public class FilterProducer {

    public static void main(String[] args) {

        try{
            DefaultMQProducer pro = new DefaultMQProducer("BatchProducer");
            pro.setNamesrvAddr("129.211.164.41:9876");
            pro.start();
            for(int i=0;i<10;i++){
                Message msg=new Message("Filteropic","Filter",("Hello_word"+i).getBytes());
                msg.putUserProperty("i",String.valueOf(i));
                SendResult send = pro.send(msg);
                System.out.println("发送结果"+send);

                TimeUnit.SECONDS.sleep(6);
            }


            pro.shutdown();

        }catch (Exception e){

        }
    }
}
