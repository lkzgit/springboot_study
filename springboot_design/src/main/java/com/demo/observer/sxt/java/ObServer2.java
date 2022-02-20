package com.demo.observer.sxt.java;

import java.util.Observable;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName ObServer2.java
 * @Description TODO java。until 提供Observer
 * @createTime 2022年02月20日 17:48:00
 */
//目标对象
public class ObServer2 extends Observable {

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void set(int s){
        state=s;// 目标对象的状态发生改变
        setChanged();// 表示目标对象已经做了更改
        notifyObservers(state); // 通知所有的观察者
    }

}
