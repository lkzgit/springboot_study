package com.model.demo.heima.itcast.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/*
 * @Description:  异步发送消息
 * @Author:lkz
 * @Date:
 **/
public class AsyncProducer {
    public static void main(String[] args) {
        try{
            DefaultMQProducer pro = new DefaultMQProducer("haoke_im_asy");
            pro.setNamesrvAddr("129.211.164.41:9876");
            pro.start();
            String msg="异步发送消息";
            Message message=new Message("haoke_im_topic","asy_send_msg",msg.getBytes(RemotingHelper.DEFAULT_CHARSET));

             pro.send(message, new SendCallback() {
                 //消息发送回调
                @Override
                public void onSuccess(SendResult send) {
                    System.out.println("消息状态：" + send.getSendStatus());
                    System.out.println("消息id：" + send.getMsgId());
                    System.out.println("消息queue：" + send.getMessageQueue());
                    System.out.println("消息offset：" + send.getQueueOffset());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("消息发送异常:"+e.getMessage());
                }


            });

            System.out.println("消息发送结束");
            //异步发送消息 注释
          //  pro.shutdown();

        }catch (Exception e){

        }


    }

}
