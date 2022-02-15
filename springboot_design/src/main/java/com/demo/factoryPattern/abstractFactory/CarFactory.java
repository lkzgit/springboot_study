package com.demo.factoryPattern.abstractFactory;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName CarFactory.java
 * @Description TODO
 * @createTime 2022年02月15日 23:54:00
 */
public interface CarFactory {
    Engine createEngine();
    Seat createSeat();
    Tyre createTyre();
}

class LuxuryFactory implements CarFactory{
    @Override
    public Engine createEngine() {
        return new LuxuryEngine();
    }

    @Override
    public Seat createSeat() {
        return new LuxurySeat();
    }

    @Override
    public Tyre createTyre() {
        return new LuxuryTyre();
    }
}

class lowFactory implements CarFactory{
    @Override
    public Engine createEngine() {
        return new lowEngine();
    }

    @Override
    public Seat createSeat() {
        return new lowSeat();
    }

    @Override
    public Tyre createTyre() {
        return new LowTyre();
    }
}