package com.demo.bridge;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName BridgeClient.java
 * @Description TODO 桥接模式 sxt
 * @createTime 2022年02月19日 17:13:00
 *
 * 问题：
 *      扩展性问题（电脑示例）
 *      如果要增加一个新的电脑类型智能手机 则要增加各个品牌下面的类
 *      如果要增肌一个新的品牌 也要增加各种电脑类型的类
 *     违反单一职责原则
 *  场景分析： 场景中有两个变化的维度：例如电脑类型 电脑品牌
 *  核心要点
 *      处理多层继承结构，处理多维度的变化场景，将各个维度设计成独立的继承结构，
 *   是各个维度可以独立的扩展在抽象层建立关系
 *
 */
public class BridgeClient {

    public static void main(String[] args) {
        Computer2 com = new Lenovo2(new Lenovo());
        com.sale();
    }
}
