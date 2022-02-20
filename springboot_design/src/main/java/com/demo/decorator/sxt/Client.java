package com.demo.decorator.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO 装饰者模式
 * @createTime 2022年02月19日 20:49:00
 *  职责； 动态的为一个对象增加一个新的功能
 *      装饰者模式是一种 用于代替继承，无须通过增加子类就可以扩展对象的信2功能
 *    使用对象的关联关系代替继承关系，更加灵活同时避免类型体系的快速膨胀。
 *  实现细节；
 *      Component抽象构建角色；
 *          真实对象和装饰对象有相同的接口，这样，客户端对象就能够以与真实对象
 *      相同的方式同装饰对象交互
 *      ConcreteComponent 具体构件角色（真实对象）
 *          io 流中的FileInputStream FileOutputStream
 *      Decorator 装饰角色
 *       持有一个抽想构件的引用。装饰对象接受所有的客户端的请求，并把这些请求转发给
 *      真实的对象 这样 就能在真实对象调用前后增加新的功能
 *      ConcreteDecorator具体装饰角色
 *       负责给构件对象增加新的特性
 *  使用场景
 *   io流中的输入输出流  request response session 对象的处理
 */
public class Client {


    public static void main(String[] args) {
        Car car=new Car();
        System.out.println("真实对象-----");
        car.move();

        System.out.println("天上飞-=----");
        FlyCar flyCar=new FlyCar(car);
        flyCar.move();

        System.out.println("水里游-------");
        WaterCar waterCar=new WaterCar(flyCar);
        waterCar.move();
    }

}
