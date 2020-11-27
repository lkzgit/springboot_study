package com.model.demo.message;

import lombok.Data;

/**
 * 参考 http://www.iocoder.cn/Spring-Boot/RocketMQ/?self
 */
@Data
public class DemoTopicMessage {


    public static final String TOPIC = "DEMO_01";
    public static final String TOPIC_02 = "DEMO_02";
    public static final String TOPIC_03 = "DEMO_03";
    public static final String TOPIC_04 = "DEMO_04";
    /**
     * @Description:  测试广播
     * @Author:lkz
     * @Date:
     **/
    public static final String TOPIC_05 = "DEMO_05";
    //顺序消费
    public static final String TOPIC_06 = "DEMO_06";
    public static final String TOPIC_07 = "DEMO_07";



    /**
     * 编号
     */
    private Integer id;

    private String msg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
