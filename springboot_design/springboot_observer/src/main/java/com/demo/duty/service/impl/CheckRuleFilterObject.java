package com.demo.duty.service.impl;

import com.demo.duty.service.AbstractHandler;
import org.omg.CORBA.Request;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;

/**
 * @author lkz
 * @date 2021/10/27 10:02
 * @description
 */
@Component
@Order(4) //校验顺序排第4
public class CheckRuleFilterObject extends AbstractHandler {

    @Override
    public void doFilter(Request request, Response response) {
        //check rule
        System.out.println("check rule");
    }
}