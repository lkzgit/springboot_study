package com.model.demo.demo;






import com.model.demo.producer.Demo07Producer;
import lombok.extern.slf4j.Slf4j;
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
public class Demo07ProducerTest {


    @Autowired
    private Demo07Producer producer;

    @Test
    public void testSendMessageInTransaction() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.sendMessageInTransaction(id);
        log.info("[testSendMessageInTransaction][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
