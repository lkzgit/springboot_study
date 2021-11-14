package com.demo.gupao.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName BioServer.java
 * @Description TODO
 * @createTime 2021年11月13日 21:47:00
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        //创建一个ServerSocket
        int port=8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务端启动，端口是"+port);
        //循环监听
        while(true){
            //等待客户端的连接 阻塞
            Socket accept = serverSocket.accept();
            //获取数据
            InputStream inputStream = accept.getInputStream();
            //转化
            byte[] buff=new byte[1024];
            int read = inputStream.read(buff);
            if(read>0){
                System.out.println("收到客户端的消息为："+new String(buff,0,read));
            }


        }

    }
}
