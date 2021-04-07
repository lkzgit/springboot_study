package com.rabbit.demo.transaction.confirm;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbit.demo.until.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 方式一 普通确认
 */
public class waitForConfirm {

    public static void main(String[] args) {
        Connection connection=null;
        Channel channel=null;
        try{
            connection = ConnectionUtil.getConnection();
            // 创建频道
            channel = connection.createChannel();
            //创建队列
            channel.queueDeclare("confirmQueue",true,false,false,null);
            //声明交换机
            channel.exchangeDeclare("directConfirmExchange","direct",true);
            //队列绑定交换机
            channel.queueBind("confirmQueue","directConfirmExchange","confirmRoutingKey");
            String message="普通发送者确认模式测试消息的测试消息！";
            //启动发送者确认模式
            channel.confirmSelect();
            channel.basicPublish("directConfirmExchange","confirmRoutingKey",null,message.getBytes("utf-8"));

            /**
             * 阻塞线程等待服务器返回响应 用于是否消费发送成功
             * 如果服务确认消费已经发送完成返回true ,否则返回false
             * 可以为这个方法指定一个毫秒用于确定我们的需要等待服务确认超时时间
             *如果超过了指定的时间以后则会抛出异常 需要补发消息 或者将消息缓存到redis中稍后利用定时任务补发
             * 无论是返回false 或者是异常都哟与可能发送成功有可能没有发送成功
             * 如果我们一定让消息 发送队列，如订单数据，那我们采用消息补发，可以试用递归或者是用哪个redis+定时任务
             * 发送一条数据确认一条数据
             */
            boolean b = channel.waitForConfirms();
            System.out.println("消息发送成功"+b);
        }catch (Exception e){

        }finally {
            if(channel!=null){
                try {

                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
