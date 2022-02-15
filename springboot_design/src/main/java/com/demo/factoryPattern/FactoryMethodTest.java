package com.demo.factoryPattern;

/**
 * 工厂模式
 * 简单工厂模式
 * 抽象工厂模式
 * 工厂方法模式
 */
public class FactoryMethodTest {

    public static void main(String[] args) {
        Application application= new ConcreteProductB();
        Product   product=application.getObject();
        product.method1();


    }
}
//简单工厂模式
interface Product{
    void method1();
}

class ProductA implements Product{

    @Override
    public void method1() {
        System.out.println( "ProductA.method1 executed." );
    }
}

class ProductB implements Product{

    @Override
    public void method1() {
        System.out.println( "ProductB.method1 executed." );
    }
}


// 简单工厂 模式也叫静态工厂模式 就是工厂类一般是使用静态的方法通过接受
//不同的参数来返回不同的对象实例，对于增加新产品无能为例不修改代码的化 无法扩展 不完全满足开闭原则
//
class SimpleFactory{
    public static Product createProduct(String type){
        if ("A".equals( type )){
            return new ProductA();
        }else if("B".equals(type)){
            return new ProductB();
        }
        return null;
    }
}
// 工厂方法模式 不需要修改源代码 但是随着业务的增多增加的类也会增多
interface factoryMethod{
    void createProduct();
}
class factoryMethod1 implements factoryMethod{

    @Override
    public void createProduct() {
        System.out.println("方法一");
    }
}
class factoryMethod2 implements factoryMethod{

    @Override
    public void createProduct() {
        System.out.println("方法2");
    }
}
class factoryMethod3 implements factoryMethod{

    @Override
    public void createProduct() {
        System.out.println("方法3");
    }
}





//  变化 ， 共同点  开闭原则
abstract class Application {

    // 工厂方法
    public abstract Product createProduct();

    public Product getObject() {

        Product  product=createProduct();
        // ......
        return product;
    }
}

class ConcreteProductA extends Application{

    @Override
    public Product createProduct() {
        ProductA productA=new ProductA();
        // ...
        return productA;
    }
}


class ConcreteProductB extends Application{

    @Override
    public Product createProduct() {
        ProductB productB=new ProductB();
        return productB;
    }
}

