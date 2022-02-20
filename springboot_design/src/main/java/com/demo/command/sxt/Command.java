package com.demo.command.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Command.java
 * @Description TODO
 * @createTime 2022年02月20日 15:15:00
 */
public interface Command {
    //
    void execute();
}

class ACommand implements Command{

    // 持有真正的命令执行者
    private Receiver receiver;

    public ACommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        // 命令真正执行前或后 执行相关逻辑
        receiver.action();
    }
}

class ClinetCom{
    public static void main(String[] args) {
        Command aCommand = new ACommand(new Receiver());
        Invoke invoke = new Invoke(aCommand);
        invoke.call();

    }
}
