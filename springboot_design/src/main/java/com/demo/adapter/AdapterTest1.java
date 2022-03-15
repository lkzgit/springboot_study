package com.demo.adapter;

/**
 * 适配器模式
 * 模式定义：
 *      将一个类的接口转换成客户希望的另一个接口。
 *  Adapter模式使得原本 由于接口不兼容而不能一起工作的那些类可以一起工作
 *  模式中的角色
 *   目标接口（Target） 客户所期待的接口 目标可以是具体的或者抽象的类 也可以是接口
 *   需要适配的类（Adaptee）:需要适配的类或者适配者类
 *   适配器（Adapter） 通过包装一个需要适配的对象，把接口转化成目标接口
 * 应用场景
 *      1.当你希望使用某些现有类，但其接口与您的其他代码不兼容时，请使 用适配器类。
 *      2.当你希望重用几个现有的子类，这些子类缺少一些不能添加到超类中 的公共功能时，请使用该模式。
 * 优点： 1.符合单一职责原则 2.符合开闭原则
 *JDK & Spring源码中的应用
 *  1 JDK:
     *  2 java.util.Arrays#asList()
     *  3 java.util.Collections#list()
 *      字节 字符流都是采用的适配器模式
 *  4 Spring:
     *  5 org.springframework.context.event.GenericApplicationListenerAdapter
 *  对象适配器模式
 */
public class AdapterTest1 {

    public static void main(String[] args) {
        Adaptee adaptee=new Adaptee();

        Target target = new Adapter( adaptee );
        target.output5v();
    }

}

class Adaptee{
    public int output220v(){
        return 220;
    }
}
interface Target{
    int output5v();
}
// Object Adapter 方式一 添加适配对象
class Adapter implements Target{

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee=adaptee;
    }

    @Override
    public int output5v() {
        int i=adaptee.output220v();
        //  原始电压转换
        System.out.println(String.format( "原始电压： %d v  - >  输出电压： %d  v  ",i,5 ));

        return 5;
    }
}
