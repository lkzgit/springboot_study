package com.demo.model.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    //实时监听队列中的消息 点对点发送消息
//    @JmsListener(destination = "order.queue")
//    public void orderQueue(String text){
//        System.out.println("OrderConsumer收到的消息："+text);
//    }

        @JmsListener(destination = "common.queue02")
    public void orderQueue2(String text){
        System.out.println("OrderConsumer收到的消息："+text);
    }

}
