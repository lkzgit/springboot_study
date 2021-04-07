package com.rabbit.demo.until;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    public static Connection getConnection() throws Exception {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //主机地址;默认为 localhost
        connectionFactory.setHost("129.211.164.41");
        //连接端口;默认为 5672
        connectionFactory.setPort(5672);
        //虚拟主机名称;默认为 /
        connectionFactory.setVirtualHost("/lkz");
        //连接用户名；默认为guest
        connectionFactory.setUsername("user");
        //连接密码；默认为guest
        connectionFactory.setPassword("password");

        //创建连接
        return connectionFactory.newConnection();
    }
}
