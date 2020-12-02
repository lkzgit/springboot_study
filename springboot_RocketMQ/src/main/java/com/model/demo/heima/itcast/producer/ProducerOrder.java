package com.model.demo.heima.itcast.producer;

import com.model.demo.heima.itcast.pojo.OrderStep;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class ProducerOrder {

    public static void main(String[] args) throws Exception {
        //创建消费者
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 指定nameServer
        producer.setNamesrvAddr("129.211.164.41:9876");
        //启动生产者
        producer.start();

        //构建消息集合
        List<OrderStep> orderSteps = OrderStep.buildOrders();
        //发送消息
        for(int i=0;i<orderSteps.size();i++){
            String body=orderSteps.get(i)+"";
            Message message = new Message("orderTopic", "order", "i" + i, body.getBytes());
            /**
             * 参数一：消息对象
             * 参数二：消息队列选择器
             * 参数三：选择队列的业务标识（订单iD）
             */
            SendResult send = producer.send(message, new MessageQueueSelector() {
                /**
                 *
                 * @param mqs 队列集合
                 * @param msg 消息对象
                 * @param arg 业务标识的参数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {

                    Long id = (Long) arg;//根据订单Id选择
                    long index = id % mqs.size();
                    return mqs.get((int) index);
                }
            }, orderSteps.get(i).getOrderId());
            System.out.println("发送订单完成："+send);
        }
        producer.shutdown();
    }

}
