//package com.demo.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@Slf4j
//public class YanShiConfig {
//
//
//
//    @Value("${yanshi.email.queue.name}")
//    private String emailQueue;
//    @Value("${yanshi.exchange.name}")
//    private String topicExchange;
//    @Value("${yanshi.dead.letter.queue.name}")
//    private String deadLetterQueue;
//    @Value("${yanshi.dead.letter.exchange.name}")
//    private String deadLetterExchange;
//    @Value("${yanshi.delay.queue.name}")
//    private String delayQueue;
//    @Value("${yanshi.delay.exchange.name}")
//    private String delayExchange;
//
//    @Bean
//    public Queue emailQueue() {
//
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-message-ttl", 1000*10);
//        // 绑定死信交换机
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
//    //私信队列和交换器
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
//    //延时队列
//    @Bean
//    public Queue delayQueue() {
//        return new Queue(delayQueue);
//    }
//
//    @Bean
//    CustomExchange delayExchange() {
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-delayed-type", "topic");
//        //参数二为类型：必须是x-delayed-message
//        return new CustomExchange(delayExchange, "x-delayed-message", true, false, args);
//
//    }
//
//    @Bean
//    Binding bindingDelayQueue() {
//        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(delayQueue+".#").noargs();
//    }
//
//}
