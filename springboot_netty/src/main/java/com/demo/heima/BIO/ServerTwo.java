package com.demo.heima.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *   目标：实现服务端可以同时接收多个客户端的Socket通信需求。
 *    思路：是服务端每接收到一个客户端socket请求对象之后都交给一个独立的线程来处理客户端的数据交互需求。
 *    问题：当前消息处理结束之后才能处理第二个消息
 */
public class ServerTwo {

    public static void main(String[] args) throws IOException {
        //1.注册端口
        ServerSocket ss = new ServerSocket(9999);
        //2.接收客户端Socket
        while(true){
            Socket socket = ss.accept();
            //创建独立的线程处理客户端
            new ServerThreadReaderTwo(socket).start();
        }

    }

}



 class ServerThreadReaderTwo extends Thread {

    private Socket socket;
    public ServerThreadReaderTwo(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 从socket对象中得到一个字节输入流
            InputStream is = socket.getInputStream();
            // 使用缓冲字符输入流包装字节输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while((msg = br.readLine())!=null){
                System.out.println(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
