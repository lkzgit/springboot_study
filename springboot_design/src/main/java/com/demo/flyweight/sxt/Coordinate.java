package com.demo.flyweight.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Coordinate.java
 * @Description TODO UnsharedConcreteFlyWeight共享享元类
 * @createTime 2022年02月19日 22:46:00
 */
public class Coordinate {

    private int x;

    private int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
