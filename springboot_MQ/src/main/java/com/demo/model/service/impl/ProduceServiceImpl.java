package com.demo.model.service.impl;

import com.demo.model.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import javax.jms.Destination;
import javax.jms.Topic;

@Service
public class ProduceServiceImpl implements ProduceService {


    @Autowired
    private Queue queue;

    @Autowired
    private JmsMessagingTemplate jmsTemplate; //用来发送消息到broker的对象

    //发送消息，destination是发送到的队列，message是待发送的消息
    @Override
    public void sendMessage(Destination destination, String message) {

        jmsTemplate.convertAndSend(destination, message);

    }


    //发送消息，destination是发送到的队列，message是待发送的消息
    @Override
    public void sendMessage(final String message) {
        jmsTemplate.convertAndSend( this.queue,message);

    }

    //=======发布订阅相关代码=========

    @Autowired
    private Topic topic;


    @Override
    public void publish(String msg) {
        this.jmsTemplate.convertAndSend(this.topic, msg);

    }
}
