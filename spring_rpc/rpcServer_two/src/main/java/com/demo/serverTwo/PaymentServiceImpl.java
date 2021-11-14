package com.demo.serverTwo;

import com.demo.api.IPaymentService;

/**
 * 注解方式
 */

@RpcService(IPaymentService.class)
public class PaymentServiceImpl implements IPaymentService{
    @Override
    public void doPay() {
        System.out.println("执行doPay方法");
    }
}
