package com.rabbit.demo.transaction.confirm;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbit.demo.until.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 方式三 )异步监听发送方确认模式
 */
public class waitForConfirmListen {

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
            /**
             *异步消息确认监听器，需要在发送消息前启动
             */
            channel.addConfirmListener(new ConfirmListener() {
                //消息确认以后的回调方法
                //参数 1 为被确认的消息的编号 从 1 开始自动递增用于标记当前是第几个消息
                //参数 2 为当前消息是否同时确认了多个
                //注意：如果参数 2 为true 则表示本次确认同时确认了多条消息，消息等于当前参数1 （消息编号）的所有消息
                //     全部被确认 如果为false 则表示只确认多了当前编号的消息
                public void handleAck(long l, boolean b) throws IOException {
                    System.out.println("消息被确认了 --- 消息编号："+l+"    是否确认了多条："+b);
                }
                //消息没有确认的回调方法
                //如果这个方法被执行表示当前的消息没有被确认 需要进行消息补发
                //参数 1 为没有被确认的消息的编号 从 1 开始自动递增用于标记当前是第几个消息
                //参数 2 为当前消息是否同时没有确认多个
                //注意： 如果参数2 为true 则表示小于当前编号的所有的消息可能都没有发送成功需要进行消息的补发
                //      如果参数2 为false则表示当前编号的消息没法发送成功需要进行补发
                public void handleNack(long l, boolean b) throws IOException {
                    System.out.println("消息没有被确认-----消息编号："+l+"    是否没有确认多条："+b);
                }
            });

            for(int i=0;i<10000;i++){
                channel.basicPublish("directConfirmExchange","confirmRoutingKey",null,message.getBytes("utf-8"));
            }
            System.out.println("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if(channel!=null){
//                try {
//                    channel.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (TimeoutException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(connection!=null){
//                try {
//                    connection.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

}
