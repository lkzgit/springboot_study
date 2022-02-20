package com.demo.state;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Context.java
 * @Description TODO
 * @createTime 2022年02月20日 16:49:00
 */
public class Context {

    private State state;

    // 该方法用来设置不同状态的行为
    public void setState(State s){
        System.out.println("修改的状态："+s);
        state=s;
        state.handle();

    }
}
