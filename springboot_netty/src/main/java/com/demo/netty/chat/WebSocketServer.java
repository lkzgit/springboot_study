package com.demo.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 参考文章连接 ；
 *  https://blog.csdn.net/weixin_42105432/article/details/107531722
 */
public class WebSocketServer {

    public static void main(String[] args) throws InterruptedException {
        //定义两个线程组，采用reactor主从模式,bossGroup负责处理连接请求，WorkerGroup负责处理业务请求。
        //默认是cpu核心数*2个线程去处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            //websocket基于http协议，添加http的编解码器
                            pipeline.addLast(new HttpServerCodec());

                            //对http中大数据的数据流，提供写大数据流的支持
                            pipeline.addLast(new ChunkedWriteHandler());

                            //对httpMessage进行聚合处理，聚合成FullHttpRequest或FullHttpResponse
                            pipeline.addLast(new HttpObjectAggregator(1024*64));

                            /*
                             * 处理一些繁重复杂的事情;
                             * 处理websocket的握手动作：handshaking(close/ping/pong) ping+pong=心跳
                             从Http协议升级到Websocket协议，是通过StatusCode 101（Switching Protocols）来切换的。
                             * 对于websocket来说，都是以frames进行传输，不同的数据类型frames不同
                             * 并且要指定与客户端交互的路径：ws://localhost:8088/chat
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));

                            //添加自定义的数据处理器
                            pipeline.addLast(new ChatHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(8088).sync();
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
