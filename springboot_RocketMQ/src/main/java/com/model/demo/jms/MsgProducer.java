package com.model.demo.jms;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MsgProducer {
    /**
     * 生产者的组名
     */
    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;
    //承载消息体
    private DefaultMQProducer producer;


    public DefaultMQProducer getProducer() {
        return this.producer;
    }


    @PostConstruct
    public void init() {
        //生产者的组名
        producer = new DefaultMQProducer(producerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        //如 producer.setNamesrvAddr("192.168.100.141:9876;192.168.100.142:9876;192.168.100.149:9876");
        producer.setNamesrvAddr(namesrvAddr);

        producer.setVipChannelEnabled(false);

        try {
            /**
             * Producer对象在使用之前必须要调用start初始化，只能初始化一次
             */
            producer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // producer.shutdown();  一般在应用上下文，关闭的时候进行关闭，用上下文监听器

    }
}