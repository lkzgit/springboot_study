package com.demo.bridge;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Brand.java
 * @Description TODO
 * @createTime 2022年02月19日 17:52:00
 */
public interface Brand {

    void sale();
}

class Lenovo implements Brand{
    @Override
    public void sale() {
        System.out.println("我是联想品牌电脑");
    }
}

class Dell implements Brand{
    @Override
    public void sale() {
        System.out.println("我是戴尔品牌电脑");
    }
}

