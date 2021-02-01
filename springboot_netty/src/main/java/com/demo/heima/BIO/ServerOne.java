package com.demo.heima.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 验证 客户端持续发消息  服务端反复接收消息
 *   只能执行一个消息处理
 */
public class ServerOne {

    public static void main(String[] args) throws IOException {
        //设置服务
        System.out.println("服务端启动");
        ServerSocket socket = new ServerSocket(9999);
        //2.监听客户端的请求
        Socket ss = socket.accept();
        //3.从socket管道中得到一个字节流对象
        InputStream inputStream = ss.getInputStream();
        //4.把字节输入流包装成一个缓冲字符输入流
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        //5.读取数据
        String msg;
        while ((msg=reader.readLine())!=null){
            System.out.println("服务端接收到的消息："+msg);
        }


    }
}
