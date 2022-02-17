package com.demo.create.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName SxtAirShipBulder.java
 * @Description TODO 构建者的实现类
 * @createTime 2022年02月16日 21:40:00
 */
public class SxtAirShipBuilder implements AirShopBuilder{
    @Override
    public Engine builderEngine() {
        System.out.println("构建sxt发动机");
        return new Engine("尚学堂发动机");
    }

    @Override
    public OrbitalModule builderOrbitalModule() {
        System.out.println("构建sxt轨道舱");
        return new OrbitalModule("尚学堂轨道舱");
    }

    @Override
    public EscapeTower builderEscapeTower() {
        System.out.println("构建sxt逃逸塔");
        return new EscapeTower("尚学堂逃逸塔");
    }
}
