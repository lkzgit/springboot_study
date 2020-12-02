package com.model.demo.heima.itcast.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;


/**
 * @Description: 测试发送消息 同步消息
 * @Author:lkz
 * @Date:
 **/
public class SyncProducer {

    public static void main(String[] args) {
        try{
            DefaultMQProducer pro = new DefaultMQProducer("haoke_im");
            pro.setNamesrvAddr("129.211.164.41:9876");
            pro.start();
            String msg="发送同步消息";
         Message message=new Message("haoke_im_topic","send_msg",msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult send = pro.send(message);
            System.out.println("消息状态：" + send.getSendStatus());
            System.out.println("消息id：" + send.getMsgId());
            System.out.println("消息queue：" + send.getMessageQueue());
            System.out.println("消息offset：" + send.getQueueOffset());
            pro.shutdown();

        }catch (Exception e){

        }


    }
}
