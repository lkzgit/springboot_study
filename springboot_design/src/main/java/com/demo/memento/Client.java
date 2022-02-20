package com.demo.memento;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO
 * @createTime 2022年02月20日 18:12:00
 * 备忘录模式；
 *    核心；保存某个对象内部状态的拷贝，这样以后就可将该对象恢复原先状态
 *    结构；
 *      源发器类；Originator
 *      备忘录类；Memento
 *      负责人类 CareTake
 *
 *
 *
 */
public class Client {

    public static void main(String[] args) {
        CareTaker c=new CareTaker();
        Emp emp=new Emp("KK",23);
        System.out.println("第一次打印对象："+emp.getName()+"--"+emp.getAge());
        c.setMement(emp.mement());//备忘录第一一次
        System.out.println("修改参数值-----");
        emp.setAge(34);
        emp.setName("hh");
        System.out.println("第er次打印对象："+emp.getName()+"--"+emp.getAge());
        System.out.println("************");
        emp.recovery(c.getMement());//恢复备忘录保存之前的状态
        System.out.println("第san次打印对象："+emp.getName()+"--"+emp.getAge());







    }

}
