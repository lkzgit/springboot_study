package com.demo.observer.sxt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Subjects.java
 * @Description TODO 主题对象
 * @createTime 2022年02月20日 17:22:00
 */
public class Subjects {

    protected List<Observer> list=new ArrayList<>();

    public void regis(Observer ob){
        list.add(ob);
    }
    public void remove(Observer ob){
        list.remove(ob);
    }

    // 通知所有观察者
    public void nofiyAllObService(){
        for (Observer item : list) {
            item.update( this );
        }
    }


}
