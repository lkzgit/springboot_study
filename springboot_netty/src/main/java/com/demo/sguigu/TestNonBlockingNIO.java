package com.demo.sguigu;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

//非阻塞模式
public class TestNonBlockingNIO {

    @Test
    public void client() throws IOException {
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

    //服务端
    @Test
    public void server() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        //设置非阻塞模式
        serverChannel.configureBlocking(false);
        //绑定连接
        serverChannel.bind(new InetSocketAddress(8888));
        //获取选择器
        Selector selector = Selector.open();
        //奖通道注册到多路复用器上 并且指定监听接收事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功--");
        //轮询获取选择器上已经准备就绪的事件
         while (selector.select()>0){
             System.out.println("新的准备就绪事件----");
             //获取当前选择器中所有注册的已经准备就绪的监听事件
             Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
             while (iterator.hasNext()){
                 SelectionKey key = iterator.next();
                 //判断key是什么具体的就绪事件
                 if(key.isAcceptable()){
                     //如果是接收就绪，获取客户端连接
                     System.out.println("有新的连接---");
                     SocketChannel sChannel = serverChannel.accept();
                     //设置非阻塞模式
                     sChannel.configureBlocking(false);
                     //将通道注册到选择器上
                     sChannel.register(selector,SelectionKey.OP_READ);
                 }else if(key.isReadable()){
                     //获取当前选择器上的通道 读就绪
                     SocketChannel sChannel=(SocketChannel) key.channel();
                     //设置缓冲区
                     ByteBuffer buffer = ByteBuffer.allocate(1024);
                     //将通道中的数据读取到缓冲区
                     int len = sChannel.read(buffer);
                     if(len>0){
                         buffer.flip();
                         System.out.println(new String(buffer.array(), 0, len));
                         buffer.clear();
//                         System.out.println("接收到的消息："+new String(buffer.array()));
                         buffer.clear();
                     }else if(len==-1){
                         System.out.println("客户端断开连接");
                         sChannel.close();
                     }
                 }
                 //从事件集合中移除
                 iterator.remove();
             }
         }

    }


}
