package com.demo.memento;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName CareTaker.java
 * @Description TODO 负责人类 负责管理备忘录对象
 * @createTime 2022年02月20日 18:24:00
 */
public class CareTaker {

    private EmpMement mement; // 可以时多个 使用容器集合List

    public EmpMement getMement() {
        return mement;
    }

    public void setMement(EmpMement mement) {
        this.mement = mement;
    }
}
