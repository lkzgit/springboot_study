package com.demo.create;

public class Bottle implements Packing{

    @Override
    public String pack() {
        System.out.println("------Bottle");
        return "Bottle";
    }
}
