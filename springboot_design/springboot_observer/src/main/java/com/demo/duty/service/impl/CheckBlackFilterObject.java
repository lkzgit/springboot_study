package com.demo.duty.service.impl;

import com.demo.duty.service.AbstractHandler;
import org.omg.CORBA.Request;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;

/**
 * @author lkz
 * @date 2021/10/27 10:01
 * @description
 */
@Component
@Order(3) //校验顺序排第3
public class CheckBlackFilterObject extends AbstractHandler {

    @Override
    public void doFilter(Request request, Response response) {
        //invoke black list check
        System.out.println("校验黑名单");
    }
}