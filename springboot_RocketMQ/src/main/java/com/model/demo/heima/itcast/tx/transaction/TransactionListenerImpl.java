package com.model.demo.heima.itcast.tx.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.HashMap;
import java.util.Map;


public class TransactionListenerImpl implements TransactionListener {

    private static Map<String, LocalTransactionState> STATE_MAP = new HashMap<>();

    /** 执行具体的业务逻辑
     * ** @param msg 发送的消息对象
     * * @param arg * @return
     * */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object o) {
        try {
            System.out.println("用户A账户减500元.");
            Thread.sleep(500); //模拟调用服务

           //  System.out.println(1/0);
             System.out.println("用户B账户加500元.");
             Thread.sleep(800);
             STATE_MAP.put(msg.getTransactionId(), LocalTransactionState.COMMIT_MESSAGE);
            // 二次提交确认
            // return LocalTransactionState.COMMIT_MESSAGE;
            //测试回查
            return LocalTransactionState.UNKNOW;
        } catch (Exception e) {
            e.printStackTrace();
        }
        STATE_MAP.put(msg.getTransactionId(), LocalTransactionState.ROLLBACK_MESSAGE);
            // 回滚
         return LocalTransactionState.ROLLBACK_MESSAGE;

    }
    /**
     * @Description:  消息回查
     * @Author:lkz
     * @Date:
     **/
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("事务进行回查");
        return STATE_MAP.get(msg.getTransactionId());
    }
}
