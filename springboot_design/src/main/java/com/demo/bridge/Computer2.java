package com.demo.bridge;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Computer2.java
 * @Description TODO
 * @createTime 2022年02月19日 18:12:00
 */
public class Computer2 {

    protected Brand brand; //电脑自身自带品牌属性

    public Computer2(Brand brand) {
        this.brand = brand;
    }

    public void sale(){
        this.brand.sale();
    }
}

class Lenovo2 extends Computer2{

    public Lenovo2(Brand brand) {
        super(brand);
    }

    @Override
    public void sale() {
        super.sale();
        System.out.println("联想 品牌的电脑");
    }
}

    class Dell2 extends Computer2{

    public Dell2(Brand brand) {
        super(brand);
    }

    @Override
    public void sale() {
        super.sale();
        System.out.println("dell 品牌的电脑");
    }
}