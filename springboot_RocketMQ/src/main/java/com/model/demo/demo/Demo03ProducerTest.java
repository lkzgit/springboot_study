package com.model.demo.demo;

import com.model.demo.producer.Demo03Producer;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
/**
 * @Description:
 * @Author:lkz 定时消息发送测试
 * @Date:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo03ProducerTest {



    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo03Producer producer;

    @Test
    public void testSyncSendDelay() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSendDelay(id, 3); // 延迟级别 3 ，即 10 秒后消费
        logger.info("发送定时消息");
        logger.info("[testSyncSendDelay][发送编号：[{}] 发送结果：[{}]]\n"+ new Date(), id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
