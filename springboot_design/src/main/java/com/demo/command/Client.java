package com.demo.command;



/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java sxt
 * @Description TODO 命令模式
 * @createTime 2022年02月20日 15:01:00
 *  概念；将一个请求封装为一个对象 从而使我们可用不同的请求 对客户端进行参数化；
 * 对请求排队或者记录请求日志 ，以及支持可撤销的操作。
 *  结构；
 *     Command抽象命令
 *     ConcreteCommand具体命令类
 *     Invoker调用者、请求者；请求的发送者，他通过命令对象来执行请求。一个调用者
 *  并不需要在设计时确认接收者，因此它与抽象命令之间存在关联。在程序运行时，将调用
 *  命令对象的execute(),简介调用接收者的相关操作
 *      Receiver 接收者； 接收者执行与请求的相关操作，具体实现对业务处理
 *                      未抽象前 实际执行操作内容的对象
 *     Client客户类；在客户类中需要创建调用着对象，具体命令类对象，在创建具体命令
 *  对象时指定对应的接收者，发送者和接收者之间没有直接的关系，都通过命令对象简介调用。
 *   应用场景； 数据库事物机制的底层实现
 *          Struts2，action的整个调用
 *
 */
public class Client {


}
