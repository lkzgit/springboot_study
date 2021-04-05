package demo01.transaction.trancWay;


import com.rabbitmq.client.*;
import demo01.until.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) {
        Connection connection=null;
        Channel channel=null;
        try{
            connection = ConnectionUtil.getConnection();
            // 创建频道
            channel = connection.createChannel();
            //创建队列
            channel.queueDeclare("transactionQueue",true,false,false,null);
            //声明交换机
            channel.exchangeDeclare("directTransactionExchange","direct",true);
            //队列绑定交换机
            channel.queueBind("transactionQueue","directTransactionExchange","transactionRoutingKey");
            /**
             * 开启事务
             * 当消费者开启事务以后，即使不作为事务的提交，那么依然可以获取队列中的
             * 消息并且将消息从队列中移除掉
             * 注意：
             *   暂时事务队列接收者没有任何的影响
             */
            channel.basicConsume("transactionQueue",true,"",new DefaultConsumer(channel){
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message=new String(body);
                    System.out.println("消费者 ---"+message);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
