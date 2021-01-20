package com.demo.sguigu;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * 一、使用NIO 完成网络通信的三个核心：
 *
 * 1、通道(Channel):负责连接
 *      java.nio.channels.Channel 接口：
 *           |--SelectableChannel
 *               |--SocketChannel
 *               |--ServerSocketChannel
 *               |--DatagramChannel
 *
 *               |--Pipe.SinkChannel
 *               |--Pipe.SourceChannel
 *
 * 2.缓冲区(Buffer):负责数据的存取
 *
 * 3.选择器(Selector):是 SelectableChannel 的多路复用器。用于监控SelectableChannel的IO状况
 */
//阻塞式
public class BlockingNio {

    //客服端
    @Test
    public void client() throws IOException {
        SocketChannel clientChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while (inChannel.read(buf)!= -1){
            buf.flip();
            clientChannel.write(buf);
            buf.clear();
        }
        clientChannel.shutdownOutput();
        System.out.println(System.currentTimeMillis());
        int len = 0;
        while ((len = clientChannel.read(buf)) != -1){
            buf.flip();
            System.out.println(new String(buf.array(),0,len));
            buf.clear();
        }
        //4.关闭通道
        clientChannel.close();
        inChannel.close();

    }

    //服务端
    @Test
    public void server() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        FileChannel outChannel = FileChannel.open(Paths.get("5.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        serverChannel.bind(new InetSocketAddress(9898));
        SocketChannel clientChannel = serverChannel.accept();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while (clientChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        System.out.println(System.currentTimeMillis());
        buf.put("服务器接收数据成功".getBytes());
        buf.flip();
        clientChannel.write(buf);
        //6.关闭通道
        clientChannel.close();
        outChannel.close();
        serverChannel.close();

    }

}
