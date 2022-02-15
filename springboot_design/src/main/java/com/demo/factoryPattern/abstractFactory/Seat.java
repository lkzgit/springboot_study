package com.demo.factoryPattern.abstractFactory;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Seat.java
 * @Description TODO
 * @createTime 2022年02月15日 23:50:00
 */
public interface Seat {
    void massage();
}

class LuxurySeat implements Seat{
    @Override
    public void massage() {
        System.out.println("自动按摩");
    }
}

class lowSeat implements Seat{
    @Override
    public void massage() {
        System.out.println("不能按摩");
    }
}