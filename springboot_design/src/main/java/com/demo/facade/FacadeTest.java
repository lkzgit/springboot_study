package com.demo.facade;

/**
 * 门面模式
 * 模式定义：
 *  为子系统中的一组接口提供一个一致的接口，Facade 模式定义了一个 高层接口，这个接口使得这一子系统更加容易使用
 * 应用场景
 *  1.当您需要使用复杂子系统的有限但直接的接口时，请使用Facade模 式。
 *  2.当您想要将子系统组织成层时，请使用Facade。
 *  优点：
 *      简化客户端的调用
 * 源码中的经典应用
 * 应用层 controller 等
 * 1 org.apache.catalina.connector.RequestFacade
 *
 */
public class FacadeTest {
    public static void main(String[] args) {

    }

}


class Client1 {

    Facade facade=new Facade();

    public void doSomething1() {
        facade.doSomethingFacade();
    }

}

class Client2 {

    Facade facade=new Facade();

    public void doSomething2() {
        facade.doSomethingFacade();
    }

}


class Facade {

    SubSystem1 subSystem1=new SubSystem1();
    SubSystem2 subSystem2=new SubSystem2();
    SubSystem3 subSystem3=new SubSystem3();

    public void doSomethingFacade() {
        subSystem1.method1();
        subSystem2.method2();
        subSystem3.method3();
    }


}


class SubSystem1 {
    public void method1() {
        System.out.println( " SubSystem1.method1 executed. " );
    }
}

class SubSystem2 {
    public void method2() {
        System.out.println( " SubSystem2.method2 executed. " );
    }
}

class SubSystem3 {
    public void method3() {
        System.out.println( " SubSystem3.method3 executed. " );
    }
}
