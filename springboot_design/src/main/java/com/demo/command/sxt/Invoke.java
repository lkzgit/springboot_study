package com.demo.command.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Invoke.java
 * @Description TODO 命令的调用者、发起者
 * @createTime 2022年02月20日 15:19:00
 */
public class Invoke {

    private Command command;// 也可以通过容器List<Command> 容纳很多命令进行批处理

    public Invoke(Command command) {
        this.command = command;
    }
    //业务方法 用来调用命令类的方法
    public void call(){
        command.execute();
    }
}
