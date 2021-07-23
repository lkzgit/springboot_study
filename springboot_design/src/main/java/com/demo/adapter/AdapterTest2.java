package com.demo.adapter;

/**

 *
 * 类适配器模式
 */
public class AdapterTest2 {

    public static void main(String[] args) {
        Adpater2 adpater=new Adpater2();
        adpater.output5v();

    }
}

class Adaptee2{
    public int output220v(){
        return 220;
    }
}
interface Target2 {
    int output5v();
}

class Adpater2 extends Adaptee2 implements Target2{

    @Override
    public int output5v() {
        int i= output220v();
        //  ......
        System.out.println(String.format( "原始电压： %d v  - >  输出电压： %d  v  ",i,5 ));
        return 5;
    }

}
