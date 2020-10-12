package com.demo.model.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务类
 */
//@Component
public class TestTask {

    /**
     * 定时任务
     */
    //@Scheduled(fixedRate =2000) //表示2秒会生成一条记录
    public void sun(){
        System.out.println("fixedRate当前的时间:"+new Date());
    }
   // @Scheduled(cron = "*/2 * * * * *") //每两秒启动一次 crontab 工具  https://tool.lu/crontab/
    public void sun2(){
        System.out.println("cron当前的时间:"+new Date());
    }

    @Scheduled(fixedDelay =2000) //上一次执行结束时间点后xx秒再次执行
    public void sun3() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("fixedDelay当前的时间:"+new Date());
    }
    @Scheduled(fixedDelayString="2000") //字符串形式，可以通过配置文件指定
    public void sun4() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("fixedDelayString:  当前的时间:"+new Date());
    }

}
