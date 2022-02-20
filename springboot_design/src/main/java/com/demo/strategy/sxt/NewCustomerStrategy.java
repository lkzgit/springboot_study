package com.demo.strategy.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName NewCustomerStrategy.java
 * @Description TODO
 * @createTime 2022年02月20日 15:49:00
 */
public class NewCustomerStrategy implements Strategy{
    @Override
    public double getPrice(double standarPrice) {

        System.out.println("普通客户 不打折");
        return standarPrice;

    }
}
