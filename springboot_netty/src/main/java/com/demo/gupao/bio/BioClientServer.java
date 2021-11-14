package com.demo.gupao.bio;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;


public class BioClientServer {

    public static void main(String[] args) throws IOException {

        /**
         *需要通信的的服务器Ip 以及端口
         */
        Socket socket = new Socket("localhost", 8080);
        //需要输出的
        OutputStream outputStream = socket.getOutputStream();
        String msg="你好！我是"+ UUID.randomUUID();
        System.out.println("客户端发送数据");
        outputStream.write(msg.getBytes());

        outputStream.flush();
       // outputStream.close();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes=new byte[1024];
        int read = inputStream.read(bytes);
        if(read>0){
            String s = new String(bytes);
            System.out.println("接收服务端的数据："+s);
        }
        socket.close();


    }
}
