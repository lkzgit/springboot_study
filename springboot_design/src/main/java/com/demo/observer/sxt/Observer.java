package com.demo.observer.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Observer.java
 * @Description TODO 观察者
 * @createTime 2022年02月20日 17:21:00
 */
public interface Observer {

    void update(Subjects sub);
}

class ObserverA implements Observer{

    private int mysate;//需要和目标对象的state的值保持一致

    public int getMysate() {
        return mysate;
    }

    public void setMysate(int mysate) {
        this.mysate = mysate;
    }

    @Override
    public void update(Subjects sub) {
        mysate=((SubA)sub).getState();
    }
}