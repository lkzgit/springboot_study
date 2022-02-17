package com.demo.create.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName SxtAirShipDirector.java
 * @Description TODO 装配者
 * @createTime 2022年02月16日 21:47:00
 */
public class SxtAirShipDirector implements AirShopDirector{
    // 装配者调用构建者创建对象
    private AirShopBuilder builder;

    public SxtAirShipDirector(AirShopBuilder builder) {
        this.builder = builder;
    }

    @Override
    public AirShip directAirShip() {
        Engine engine = builder.builderEngine();// 调用获取组件
        EscapeTower escapeTower = builder.builderEscapeTower();
        OrbitalModule orbitalModule = builder.builderOrbitalModule();
        //组装对象
        AirShip ship=new AirShip();
        ship.setEngine(engine);
        ship.setEscapeTower(escapeTower);
        ship.setOrbitalModule(orbitalModule);

        return ship;
    }
}
