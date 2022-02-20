package com.demo.strategy.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName NewCustomerStrategy.java
 * @Description TODO
 * @createTime 2022年02月20日 15:49:00
 */
public class NewCustomerOldStrategy implements Strategy{
    @Override
    public double getPrice(double standarPrice) {

        System.out.println("老客户 0.8");
        return standarPrice*0.8;

    }
}
