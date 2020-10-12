package com.demo.model.service;

import javax.jms.Destination;

/**
 * 消息生产者
 */
public interface ProduceService {

    /**
     * 功能描述：指定消息队列，还有消息
     * @param destination
     * @param message
     */
    void sendMessage(Destination destination,final String message);

    /**
     * 功能描述：使用默认消息队列， 发送消息
     * @param message
     */
    public void sendMessage( final String message);


    /**
     * 功能描述：消息发布者
     * @param msg
     */
    public void publish(String msg);
}
