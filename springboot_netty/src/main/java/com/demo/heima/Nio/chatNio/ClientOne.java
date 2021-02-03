package com.demo.heima.Nio.chatNio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ClientOne {

    public static void main(String[] args) throws IOException {
        //建立连接
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 9999));
        // 2.切换非阻塞模式
        channel.configureBlocking(false);
        //3.指定分配缓冲区大小
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //4.发送数据给客户端
        Scanner sc = new Scanner(System.in);
            while (sc.hasNext()){
                String str = sc.nextLine();
                buf.put((new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis())
                        + "\n" + str).getBytes());

                buf.flip();
                channel.write(buf);
                buf.clear();

            }
            channel.close();

    }
}
