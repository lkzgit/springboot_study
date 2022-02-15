package com.demo.factoryPattern.abstractFactory;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Tyre.java
 * @Description TODO
 * @createTime 2022年02月15日 23:52:00
 */
public interface Tyre {
    void revolve();

}
class LuxuryTyre implements Tyre{
    @Override
    public void revolve() {
        System.out.println("高端摩檫力小");
    }
}

class LowTyre implements Tyre{
    @Override
    public void revolve() {
        System.out.println("低端摩檫力大");
    }
}