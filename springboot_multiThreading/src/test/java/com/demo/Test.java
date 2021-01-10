package com.demo;

public class Test {

    @org.junit.jupiter.api.Test
    public void Test(){
        int i=10;
        i=++i;
        System.out.println(i);
        int t=i;
        i=i+1;
        i=t;
        System.out.println(t);
    }
}
