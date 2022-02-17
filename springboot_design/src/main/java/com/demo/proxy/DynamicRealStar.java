package com.demo.proxy;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName RealStar.java
 * @Description TODO 动态代理
 * @createTime 2022年02月17日 23:21:00
 */
public class DynamicRealStar implements DynamicStar
{
    @Override
    public void confer() {
        System.out.println("RealStar confer");
    }

    @Override
    public void signContract() {
        System.out.println("RealStar  signContract");
    }

    @Override
    public void bookTicket() {
        System.out.println("RealStar  bookTicket");
    }

    @Override
    public void sign() {
        System.out.println("RealStar  sign");
    }
}
