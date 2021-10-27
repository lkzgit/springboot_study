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
@Order(2) //校验顺序排第2
public class CheckSecurityFilterObject extends AbstractHandler {

    @Override
    public void doFilter(Request request, Response response) {
        //invoke Security check
        System.out.println("安全调用校验");
    }
}
