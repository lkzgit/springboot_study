package com.demo.create.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName AirShipClient.java
 * @Description TODO 测试
 * @createTime 2022年02月16日 23:02:00
 *  应用常见
 *      StringBuilder 类中的append方法
 *      Sql中的PreparedStatement
 *      JDOM中 DomBuilder SAXbuilder
 */
public class AirShipClient {

    public static void main(String[] args) {
        //获取配置类
        AirShopDirector ship= new SxtAirShipDirector(new SxtAirShipBuilder());
        AirShip airShip = ship.directAirShip();
        String name = airShip.getEngine().getName();
        System.out.println(name);
    }
}
