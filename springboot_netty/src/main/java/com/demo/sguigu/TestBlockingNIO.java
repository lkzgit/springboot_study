package com.demo.sguigu;


import cn.hutool.core.util.URLUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 1.管道（Channel）负责连接
 * <p>
 * 2.缓冲区（Buffer）：复制数据读取
 * <p>
 * 2.选择器（Selector）：是SelectableChannel的多路复用。用于监控SelectableChannel的IO状况
 */
//阻塞式
public class TestBlockingNIO {




    //客户端
    @Test
    public void client() throws IOException {
        //获取通道
        InetSocketAddress netAdress = new InetSocketAddress("127.0.0.1", 8888);
        SocketChannel channel = SocketChannel.open(netAdress);

        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        //指定分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取本地文件 发送客户端
        while (channel.read(buffer)!=-1){
            buffer.flip();//转化读模式
            channel.write(buffer);
            buffer.clear();
        }
        channel.close();
        inChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException {
        //1.获取通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        FileChannel outChannel = FileChannel.open(Paths.get("6.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //2.绑定连接
        serverChannel.bind(new InetSocketAddress(8888));
        //3.分配缓冲区大小
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //4.获取客服端连接的通道
        SocketChannel clientChannel = serverChannel.accept();

        //5.接收客户端的数据，保存到本地
        while (clientChannel.read(buf) != -1) {
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        //6.关闭通道
        clientChannel.close();
        outChannel.close();
        serverChannel.close();

    }

}
