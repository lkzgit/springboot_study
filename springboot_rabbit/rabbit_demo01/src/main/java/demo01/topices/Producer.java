package demo01.topices;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import demo01.until.ConnectionUtil;

/**
 * 通配符Topic的交换机类型为：topic
 * 试用不同的功能模块接收不同的消息模块
 * 例如：商城下单，通过routing.key 下单成功 物流监听消息 key.*，发票监听消息 key.*
 *
 * 可以指定队列名称，也可以试用随机的一个队列 ，如果应用订单，建议试用指定队列 ，并且指定持久化
 */
public class Producer {

    //交换机名称
    static final String TOPIC_EXCHAGE = "topic_exchange";
    //队列名称
    static final String TOPIC_QUEUE_1 = "topic_queue_1";
    //队列名称
    static final String TOPIC_QUEUE_2 = "topic_queue_2";


    public static void main(String[] args) throws Exception {

        //创建连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        /**
         * 声明交换机
         * 参数1：交换机名称
         * 参数2：交换机类型，fanout、topic、topic、headers
         */
        channel.exchangeDeclare(TOPIC_EXCHAGE, BuiltinExchangeType.TOPIC);
//        // 声明（创建）队列 在web页面进行配置
//        /**
//         * 参数1：队列名称
//         * 参数2：是否定义持久化队列
//         * 参数3：是否独占本次连接
//         * 参数4：是否在不使用的时候自动删除队列
//         * 参数5：队列其它参数
//         */
//        channel.queueDeclare(TOPIC_QUEUE_1, true, false, false, null);
//        channel.queueDeclare(TOPIC_QUEUE_2, true, false, false, null);
//        channel.queueDeclare(TOPIC_QUEUE_3, true, false, false, null);
//
//        //队列绑定交换机
//        channel.queueBind(TOPIC_QUEUE_1, TOPIC_EXCHAGE, "item.insert");
//        channel.queueBind(TOPIC_QUEUE_2, TOPIC_EXCHAGE, "item.update");
//


        // 发送信息
        String message = "新增了商品。Topic模式；routing key 为 item.insert " ;
        channel.basicPublish(TOPIC_EXCHAGE, "item.insert", null, message.getBytes());
        System.out.println("已发送消息：" + message);

        // 发送信息
        message = "修改了商品。Topic模式；routing key 为 item.update" ;
        channel.basicPublish(TOPIC_EXCHAGE, "item.update", null, message.getBytes());
        System.out.println("已发送消息：" + message);

        // 发送信息
        message = "删除了商品。Topic模式；routing key 为 item.delete" ;
        channel.basicPublish(TOPIC_EXCHAGE, "item.delete", null, message.getBytes());
        System.out.println("已发送消息：" + message);

        // 关闭资源
        channel.close();
        connection.close();
    }

}
