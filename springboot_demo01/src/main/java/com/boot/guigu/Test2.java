package com.boot.guigu;

import org.junit.Test;

public class Test2 {

    static int s;// 5 成员标量 类变量
    int i; // A-2  B-1 成员变量
    int j;//A-1 B-1 成员变量
    {   //  局部变量 非静态代码块的执行：每次创建实例对象都会执行
        int i = 1;
        i++; // 就近原则
        j++;
        s++;
    }
    public void test(int j) { //形参 局部变量
        j++; // 就近原则 21
        i++;
        s++;
    }
    public static void main(String[] args){ // 形参 局部变量
        Test2 obj1 = new Test2();
        Test2 obj2 = new Test2();
        obj1.test(10);
        obj1.test(20);
        obj2.test(30);
        // 2 1 5
        System.out.println(obj1.i + "," + obj1.j + "," + obj1.s);
        // 1 1 5
        System.out.println(obj2.i + "," + obj2.j + "," + obj2.s);
    }




}
