package com.demo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class YSMessageHandler {

    /**
     * 邮件发送
     * @param message
     * @param channel
     * @param headers
     * @throws IOException
     */
    @RabbitListener(queues ="demo.email")
    @RabbitHandler
    public void handleEmailMessage(Message message, Channel channel, @Headers Map<String,Object> headers) throws IOException {

        try {

            String msg=new String(message.getBody(), CharEncoding.UTF_8);
            JSONObject jsonObject = JSON.parseObject(msg);
            jsonObject.put("messageId",headers.get("spring_returned_message_correlation"));
            log.info("---接受到消息---{}",jsonObject);
            //主动异常
            int m=1/0;
            //手动签收
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
        catch (Exception e) {
            log.info("handleEmailMessage捕获到异常,拒绝重新入队---消息ID---{}", headers.get("spring_returned_message_correlation"));
            //异常，ture 重新入队,或者false,进入死信队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);

        }
    }

    /**
     * 死信消费者，自动签收开启状态下，超过重试次数，或者手动签收，reject或者Nack
     * @param message
     */
    @RabbitListener(queues = "demo.dead.letter")
    public void handleDeadLetterMessage(Message message, Channel channel,@Headers Map<String,Object> headers) throws IOException {

        //可以考虑数据库记录，每次进来查数量，达到一定的数量，进行预警，人工介入处理
        log.info("接收到死信消息:---{}---消息ID---{}",
       new String(message.getBody()),headers.get("spring_returned_message_correlation"));
        //回复ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    /**
     * 延时队列消费
     * @param message
     * @param channel
     * @param headers
     * @throws IOException
     */
    @RabbitListener(queues ="demo.delay")
    @RabbitHandler
    public void handleOrderDelayMessage(Message message, Channel channel, @Headers Map<String,Object> headers) throws IOException {

        try {

            String msg=new String(message.getBody(), CharEncoding.UTF_8);
            JSONObject jsonObject = JSON.parseObject(msg);
            log.info("---接受到订单消息---orderId---{}",message.getMessageProperties().getMessageId());
            log.info("---订单信息---order---{}",jsonObject);
            //业务逻辑，根据订单id获取订单信息，如果还未支付，设置关闭状态，如果已支付，不做任何处理
            //手动签收
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
        catch (Exception e) {
            log.info("handleOrderDelayMessage捕获到异常,重新入队---orderId---{}", headers.get("spring_returned_message_correlation"));
            //异常，ture 重新入队,或者false,进入死信队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);

        }
    }


}
