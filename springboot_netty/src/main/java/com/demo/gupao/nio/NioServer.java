package com.demo.gupao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName NioServer.java
 * @Description TODO 解决单一连接 多路复用机制
 * @createTime 2021年11月13日 22:14:00
 */
public class NioServer {

    private static Selector selector;

    private static  ByteBuffer buffer=ByteBuffer.allocate(1024);

    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        //设置地址
        open.bind(new InetSocketAddress(8080));
        //Bio升级NiO 为了兼容Bio Nio采用导入是阻塞式
        open.configureBlocking(false);
        selector = Selector.open();
        open.register(selector, SelectionKey.OP_ACCEPT);

        while (true){

            int select = selector.select();
            //获取所有的连接
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //不断迭代，轮询每次拿去一个key 只能处理一个状态
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                //移除以获取的
                iterator.remove();
                //处理请求 针对每一种状态
                if(key.isAcceptable()){
                    //获取服务端channel
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                        //这个方法体现非阻塞 不管有没有数据准备好 给我一个状态和反馈
                    SocketChannel accept = channel.accept();
                    //一定设置非阻塞
                    accept.configureBlocking(false);
                    // 准备就绪 状态改为可读状态
                    key=accept.register(selector,SelectionKey.OP_READ);

                }else if(key.isReadable()){
                    //key.channel 从多路复用器中拿到客户端引用 ，获取客户端channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    int read = channel.read(buffer);
                    if(read>0){
                        buffer.flip();
                        String content = new String(buffer.array(), 0, read);
                        //改为写的状态 表示下一次 可以写入
                        key=  channel.register(selector, SelectionKey.OP_WRITE);
                        //在key上携带一个附件 一会在写出去
                        key.attach(content);
                        System.out.println("读取内容：" + content);

                    }

                }else if(key.isWritable()){
                    SocketChannel channel = (SocketChannel)key.channel();
                    String content = (String)key.attachment();
                    channel.write(ByteBuffer.wrap(("输出：" + content).getBytes()));

                    channel.close();
                }

            }


        }


    }

}
