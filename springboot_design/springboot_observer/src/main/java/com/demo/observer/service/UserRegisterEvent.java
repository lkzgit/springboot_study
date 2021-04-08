package com.demo.observer.service;

import org.springframework.context.ApplicationEvent;

/**参考芋道源码
 * Spring 基于观察者模式，实现了自身的事件机制，由三部分组成：
 * 事件 ApplicationEvent：通过继承它，实现自定义事件。另外，
 * 通过它的 source 属性可以获取事件源，timestamp 属性可以获得发生时间。
 * 事件发布者 ApplicationEventPublisher：通过它，可以进行事件的发布。
 * 事件监听器 ApplicationListener：通过实现它，进行指定类型的事件的监听。
 */
public class UserRegisterEvent extends ApplicationEvent {

    /**
     * 用户名
     */
    private String username;

    public UserRegisterEvent(Object source) {
        super(source);
    }

    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
