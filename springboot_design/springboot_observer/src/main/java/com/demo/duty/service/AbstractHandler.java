package com.demo.duty.service;



import org.omg.CORBA.Request;

import javax.xml.ws.Response;

/**
 * @author lkz
 * @date 2021/10/27 9:54
 * @description
 * 责任链模式怎么使用呢？
     * 一个接口或者抽象类
     * 每个对象差异化处理
     * 对象链（数组）初始化（连起来）
 * 这个接口或者抽象类，需要：
     * 有一个指向责任下一个对象的属性
     * 一个设置下一个对象的set方法
     * 给子类对象差异化实现的方法（如以下代码的doFilter方法）
 */
public abstract class AbstractHandler {

    //责任链中的下一个对象
    private AbstractHandler nextHandler;

    /**
     * 责任链的下一个对象
     */
    public void setNextHandler(AbstractHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    /**
     * 具体参数拦截逻辑,给子类去实现
     */
    public void filter(Request request, Response response) {
        doFilter(request, response);
        if (getNextHandler() != null) {
            getNextHandler().filter(request, response);
        }
    }

    public AbstractHandler getNextHandler() {
        return nextHandler;
    }

    protected abstract void doFilter(Request filterRequest, Response response);

}
