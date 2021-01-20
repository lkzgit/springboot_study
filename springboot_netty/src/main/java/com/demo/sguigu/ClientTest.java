package com.demo.sguigu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

public class ClientTest {

    public static void main(String[] args) throws IOException {

        //获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        //切换非阻塞模式
        sChannel.configureBlocking(false);
        //分配缓冲大小
        ByteBuffer buf = ByteBuffer.allocate(1024);
//        //发送数据给服务端
//        buf.put(new Date().toString().getBytes());
//        buf.flip();
//        sChannel.write(buf);
//        buf.clear();
        Scanner scan=new Scanner(System.in);
        while (scan.hasNext()){
            String next = scan.next();
            buf.put((new Date().toString() + "\n" + next).getBytes());
            System.out.println("------------------" + (new Date().toString() + "\n" + next));
//            buf.put((new Date().toString()).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //关闭通道
        sChannel.close();

    }
}
