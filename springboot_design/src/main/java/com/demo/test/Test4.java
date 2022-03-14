package com.demo.test;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Test4.java
 * @Description TODO
 * @createTime 2022年03月12日 09:52:00
 */
public class Test4 extends AbstracTest{
    @Override
    public void test3() {
        System.out.println("****sout****");
    }


    public static void main(String[] args) {
        Test4 test4 = new Test4();
        test4.test3();
    }
}
