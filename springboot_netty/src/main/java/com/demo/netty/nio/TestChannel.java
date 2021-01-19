package com.demo.netty.nio;

import org.junit.Test;

import java.nio.channels.ByteChannel;

/**
 *  通道(channel):用于源节点与目标节点的连接，在Java NIO中负责缓冲区数据的传输，因此需要配合缓冲区进行传输
 *  二 通道的主要实现；
 *      FileChannel
 *      SocketChannel
 *      ServerSocketChannel
 *      DatagramChannel
 *   三，获取通道
 *      1.java针对支持通道提供了getChannel()方法
 *      本地IO
 *      FileInputStream/FileOutputStream
 *      RandomAccessFile
 *      网络IO
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *   获取通道的其他方式是使用 Files 类的静态方法 newByteChannel()
 *   获取字节通道。或者通过通道的静态方法 open() 打开并返回指定通道。
 */
public class TestChannel {

    @Test
    public void test1(){

    }

}
