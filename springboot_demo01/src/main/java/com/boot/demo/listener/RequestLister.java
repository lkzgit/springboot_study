package com.boot.demo.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * 常用的监听器有三种
 * 1.servletContextLister
 *      servlet上下文监听器
 * 2.HttpSessionLister
 * 3.servletRequestListener
 */
//@WebListener
public class RequestLister implements ServletRequestListener {
    /**
     * 业务进行处理的时候会调用
     * @param sre
     */
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // TODO Auto-generated method stub
        System.out.println("======requestDestroyed========");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("======requestInitialized========");

    }

}
