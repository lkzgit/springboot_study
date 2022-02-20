package com.demo.decorator.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName ICar.java
 * @Description TODO 抽象构建
 * @createTime 2022年02月19日 21:20:00
 */
public interface ICar {

    void move();
}
//ConcreteComponent 具体构件角色（真实对象）
class Car implements ICar{
    @Override
    public void move() {
        System.out.println("地上跑");
    }
}

//Decorator 装饰角色
class SuperCar implements ICar{
    protected  ICar car;

    public SuperCar(ICar car) {
        this.car = car;
    }

    @Override
    public void move() {
        car.move();
    }
}
//ConcreteDecorator具体装饰角色
class FlyCar extends SuperCar{


    public FlyCar(ICar car) {
        super(car);
    }

    public void fly(){
        System.out.println("天上飞");
    }

    @Override
    public void move() {
        super.move();
        fly();

    }
}

//ConcreteDecorator具体装饰角色
class WaterCar extends SuperCar{


    public WaterCar(ICar car) {
        super(car);
    }

    public void swim(){
        System.out.println("水里游");
    }

    @Override
    public void move() {
        super.move();
        swim();

    }
}