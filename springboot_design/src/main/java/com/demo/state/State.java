package com.demo.state;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName State.java
 * @Description TODO 状态类 统一的接口
 *  抽象状态类；封装与Context 的一个特定状态相关的行为
 * @createTime 2022年02月20日 16:44:00
 */
public interface State {
    void handle();
}

class FreeState implements State{

    @Override
    public void handle() {
        System.out.println("空闲状态");
    }
}

class BookState implements State{

    @Override
    public void handle() {
        System.out.println("预定状态");
    }
}
class CheckingState implements State{

    @Override
    public void handle() {
        System.out.println("入住状态");
    }
}