package com.demo.observer.sxt.java;

import com.demo.observer.sxt.java.ObServer2;

import java.util.Observable;
import java.util.Observer;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName ObserverA.java
 * @Description TODO 观察者
 * @createTime 2022年02月20日 17:53:00
 */
public class JavaObserver implements Observer {

    private int mystate;

    public int getMystate() {
        return mystate;
    }

    public void setMystate(int mystate) {
        this.mystate = mystate;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("我时Java 提供");

        mystate=((ObServer2)o).getState();
    }



}
