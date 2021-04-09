//package com.demo.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@Slf4j
//public class SiXinConfig {
//
//    @Value("${sixin.email.queue.name}")
//    private String emailQueue;
//    @Value("${sixin.exchange.name}")
//    private String topicExchange;
//    @Value("${sixin.dead.letter.queue.name}")
//    private String deadLetterQueue;
//    @Value("${sixin.dead.letter.exchange.name}")
//    private String deadLetterExchange;
//
//    @Bean
//    public Queue emailQueue() {
//
//        Map<String, Object> arguments = new HashMap<>();
//        // 绑定死信交换机
//        arguments.put("x-message-ttl", 1000*10);
//        arguments.put("x-dead-letter-exchange", deadLetterExchange);
//        // 绑定死信的路由key
//        arguments.put("x-dead-letter-routing-key", deadLetterQueue+".#");
//
//        return new Queue(emailQueue,true,false,false,arguments);
//    }
//
//
//    @Bean
//    TopicExchange emailExchange() {
//        return new TopicExchange(topicExchange);
//    }
//
//
//    @Bean
//    Binding bindingEmailQueue() {
//        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(emailQueue+".#");
//    }
//
//
//    //死信队列和交换器
//    @Bean
//    public Queue deadLetterQueue() {
//        return new Queue(deadLetterQueue);
//    }
//
//    @Bean
//    TopicExchange deadLetterExchange() {
//        return new TopicExchange(deadLetterExchange);
//    }
//
//    @Bean
//    Binding bindingDeadLetterQueue() {
//        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadLetterQueue+".#");
//    }
//
//
//}
