package com.demo.create.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName AirShopBuilder.java
 * @Description TODO
 * @createTime 2022年02月16日 21:36:00
 */
public interface AirShopBuilder {
    Engine builderEngine();
    OrbitalModule builderOrbitalModule();
    EscapeTower builderEscapeTower();
}
