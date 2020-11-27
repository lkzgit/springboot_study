package com.model.demo.demo;


import com.model.demo.producer.Demo06Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Demo06ProducerTest {

    @Autowired
    private Demo06Producer producer;

    @Test
    public void testSyncSendOrderly() throws InterruptedException {
        // 发送多条消息
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            SendResult result = producer.syncSendOrderly(id);
            log.info("[testSyncSendOrderly][发送编号：[{}] 发送结果：[{}]]", id, result);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testASyncSendOrderly() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            producer.asyncSendOrderly(id, new SendCallback() {

                @Override
                public void onSuccess(SendResult result) {
                    log.info("[testASyncSendOrderly][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
                }

                @Override
                public void onException(Throwable e) {
                    log.info("[testASyncSendOrderly][发送编号：[{}] 发送异常]]", id, e);
                }

            });
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testOnewaySendOrderly() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            producer.onewaySendOrderly(id);
            log.info("[testOnewaySendOrderly][发送编号：[{}] 发送完成]", id);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
