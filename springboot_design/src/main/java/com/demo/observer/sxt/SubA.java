package com.demo.observer.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName SubA.java
 * @Description TODO
 * @createTime 2022年02月20日 17:40:00
 */
public class SubA extends Subjects{

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        // 主题对象修改 通知所有的观察者
        this.nofiyAllObService();
    }
}
