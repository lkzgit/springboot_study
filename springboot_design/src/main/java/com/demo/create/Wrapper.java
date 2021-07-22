package com.demo.create;

public class Wrapper implements Packing{

    @Override
    public String pack() {
        System.out.println("------Wrapper");
        return "Wrapper";
    }
}
