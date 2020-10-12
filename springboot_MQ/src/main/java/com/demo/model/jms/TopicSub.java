package com.demo.model.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
@Component
public class TopicSub {

    /**
     *  @JmsListener  如果不指定独立的containerFactory只能消费队列消息
     *  消费者修改 containerFactory="jmsListenerContainerTopic"
     * @param text
     */
    @JmsListener(destination="video.topic", containerFactory="jmsListenerContainerTopic")
        public void receive1(String text){
            System.out.println("video.topic 消费者:receive1="+text);
        }


        @JmsListener(destination="video.topic", containerFactory="jmsListenerContainerTopic")
        public void receive2(String text){
            System.out.println("video.topic 消费者:receive2="+text);
        }


        @JmsListener(destination="video.topic", containerFactory="jmsListenerContainerTopic")
        public void receive3(String text){
            System.out.println("video.topic 消费者:receive3="+text);
        }


    }

