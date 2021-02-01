package com.demo.heima.BIO;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientOne {

    public static void main(String[] args) throws IOException {
        System.out.println("客户端启动");
        //1.创建Socket对象连接服务端的请求
        Socket ss=new Socket("127.0.0.1",9999);
        //2.从Socket对象中获取一个字节输出流
        OutputStream os = ss.getOutputStream();
        //3.把字节输出流包装成一个打印流
        PrintStream stream = new PrintStream(os);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("请说：");
            String s = scanner.nextLine();
            stream.println(s);
            stream.flush();
        }
    }
}
