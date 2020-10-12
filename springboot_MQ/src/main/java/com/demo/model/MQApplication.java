package com.demo.model;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@SpringBootApplication
@EnableJms //启动消息队列
public class MQApplication {


    /**
     * 参考 1. https://blog.csdn.net/qq_22200097/article/details/82713261
     *      2.https://www.cnblogs.com/elvinle/p/8457596.html
     * @return
     */

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("common.queue02");// 创建队列
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("video.topic");
    }

        public static void main(String[] args) {
        SpringApplication.run(MQApplication.class,args);
    }

    /**
     * 需要给topic定义独立的 JmsListenerContainer
     * 配置文件中就不需要开启topic
     * #jms:
     *     #pub-sub-domain: true #默认支持点对点 true 开启发布订阅模式
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }
}
