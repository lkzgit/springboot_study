package com.demo.create;

/**
 * 设计模式之建造者模式
 * https://thinkwon.blog.csdn.net/article/details/101383401
 */
public interface Item {

    String name();

    Packing packing();

    float price();

}

