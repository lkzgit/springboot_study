package com.demo;

public class Test {

    /**
     * 检测概数是不是2n次方
     */
    @org.junit.Test
    public void test(){
        int a=10;
        while (a%2==0){
            a=a/2;
            System.out.println(a);
            if(a==1){
                System.out.println("是："+a);
            }
            System.out.println(a);
        }


    }
}
