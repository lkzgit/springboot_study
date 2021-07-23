package com.demo.abstractFactory;

/**
 * 抽象工厂模式
 * 模式定义: 提供一个创建一系列相关或互相依赖对象的接口，而无需指定它们具体的类
 *
 * 应用场景: 程序需要处理不同系列的相关产品，但是您不希望它依赖于这些产品的 具体类时， 可以使用抽象工厂
 * 优点:
     * 1.可以确信你从工厂得到的产品彼此是兼容的。
     * 2.可以避免具体产品和客户端代码之间的紧密耦合。
     * 3.符合单一职责原则
     * 4.符合开闭原则
 * JDK源码中的应用:
     * 1 java.sql.Connection
     * 2 java.sql.Driver
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {
        IDatabaseUtils databaseUtils=new MysqlDataBase();
        Connection connection = databaseUtils.connection();
        connection.connection();
        Command command = databaseUtils.command();
        command.command();
    }
}


interface Connection{
    void connection();
}
interface  Command{
    void command();
}

interface IDatabaseUtils{
    Connection connection();
    Command command();
}

class MySqlConnection implements Connection{

    @Override
    public void connection() {
        System.out.println("mysql connection");
    }
}

class MysqlCommand implements Command{

    @Override
    public void command() {
        System.out.println("mysql ----command");
    }
}

class MysqlDataBase implements IDatabaseUtils{

    @Override
    public Connection connection() {
        return new MySqlConnection();
    }

    @Override
    public Command command() {
        return new MysqlCommand();
    }
}