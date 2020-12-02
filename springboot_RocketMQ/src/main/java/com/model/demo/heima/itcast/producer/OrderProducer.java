package com.model.demo.heima.itcast.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Description:  顺序消费
 * @Author:lkz
 * @Date:
 **/
public class OrderProducer {

    public static void main(String[] args) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("HAOKE_ORDER_PRODUCER");
            producer.setNamesrvAddr("129.211.164.41:9876");
            producer.start();
            for (int i = 0; i < 50; i++) {

                int orderId = i % 10; // 模拟生成订单id
                String msgStr = "order --> " + i+"id= " +orderId;
                Message message = new Message("haoke_order_topic","ORDER_MSG", msgStr.getBytes(RemotingHelper.DEFAULT_CHARSET));
                /**
                 * mqs:队列集合
                 * msg：消息队列
                 * arg: 业务标识参数
                 */

                SendResult sendResult = producer.send(message, (mqs, msg, arg) -> {
                    Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index); //mqs大小是根据topic下面的队列 进行取模运算
                }, orderId);
                System.out.println(sendResult);
            }
            producer.shutdown();
        }catch (Exception e){

        }

    }
}
