package com.demo.mediator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName President.java
 * @Description TODO
 * @createTime 2022年02月20日 00:32:00
 */
public class President implements Mediator{
    private Map<String,DepartMent> map=new HashMap<>();


    @Override
    public  void register(String name,DepartMent d){
        map.put(name,d);
    }

    @Override
    public void command(String dname) {
        map.get(dname).selfAction(); // 传达命令

    }


}
