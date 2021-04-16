package rabbit.demo.itheima.listener;

import org.springframework.stereotype.Component;

@Component
public class MyListener {

//    /**
//     * 监听某个队列的消息
//     * @param message 接收到的消息
//     */
//    @RabbitListener(queues = "item_queue")
//    public void myListener1(String message){
//        System.out.println("消费者接收到的消息为：" + message);
//    }

    /**
     * //@QueueBinding完成队列与交换机的绑定
     * @Queue创建一个队列，没有指定表示随机创建一个队列
      * @param messages
     */
//    @RabbitListener(bindings = {@QueueBinding(value =@Queue(),
//            exchange = @Exchange(name="fanoutExchange",type = "fanout"))})
//    public void fanoutRecevice(String messages){
//        System.out.println("广播模式接收消息："+messages);
//    }


//    @RabbitListener(bindings = {@QueueBinding(value=@Queue("topic01"),key = {"aa"},exchange =@Exchange(name = "topicExchange",type = "topic"))})
//    public void  topicReceive01(String message){
//        System.out.println("topic01消费者 ---aa---"+message );
//    }
//    @RabbitListener(bindings = {@QueueBinding(value=@Queue("topic02"),key = {"aa.*"},exchange =@Exchange(name = "topicExchange",type = "topic"))})
//    public void  topicReceive02(String message){
//        System.out.println("topic02消费者 ---aa.*---"+message );
//    }
//    @RabbitListener(bindings = {@QueueBinding(value=@Queue("topic03"),key = {"aa.#"},exchange =@Exchange(name = "topicExchange",type = "topic"))})
//    public void  topicReceive03(String message){
//        System.out.println("topic03消费者 ---aa。#---"+message );
//    }



}
