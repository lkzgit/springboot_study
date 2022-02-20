package com.demo.mediator;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Medistor.java
 * @Description TODO
 * @createTime 2022年02月20日 00:36:00
 */
public interface Mediator {

    void register(String name,DepartMent d);

    void command(String name);
}
