package com.demo.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 用于处理消息的handler
 * 由于它的传输数据载体是frame，这个frame在netty中，是用于websocket专门处理文本对象的
 * netty中用TextWebSocketFrame对象来封装frame
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 定义一个channel组，管理所有的channel。
    // GlobalEventExecutor.INSTANCE：全局事件执行器。单例。
    // 用于记录和管理所有的客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取当前channel
        Channel channel = ctx.channel();
        //遍历不同的情况clients,g根据不同的情况 回送不同的消息
        clients.forEach(ch->{
            if(channel!=ch){ //不是当前的channel转发消息
                clients.writeAndFlush(new TextWebSocketFrame("【" + ctx.channel().remoteAddress().toString() + "】： " + msg.text()));
            }else{
                System.out.println("自己发送："+ch.remoteAddress().toString());
                clients.writeAndFlush(new TextWebSocketFrame("【" +"我自己发送"+ "】： " + msg.text()));

            }
        });
//        //获取客户端传输的数据
//        String context = msg.text();
//        System.out.println("接受到的数据：" + context);
//
//        //向所有客户端发送接受到的消息
//        clients.writeAndFlush(new TextWebSocketFrame("【" + ctx.channel().remoteAddress().toString() + "】： " + context));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有新的连接加入-----");
        //当有连接建立时就将channel添加到全局变量clients中。
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //连接断开时不用去手动删除clients中的channel，DefaultChannelGroup会帮我们做
        System.out.println("客户端断开，channel对应的长ID：" + ctx.channel().id().asLongText());
        clients.forEach(ch->{
            if(channel!=ch){ //不是当前的channel转发消息
                clients.writeAndFlush(new TextWebSocketFrame("【" + ctx.channel().remoteAddress().toString() + "】： " + "下线了"));
            }
        });
    }
    /**
     * 当客户端连接服务器完成就会触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("----activiti----");
        super.channelActive(ctx);
    }
    //表示 channel 处于不活动状态, 提示离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("---------");
        super.channelInactive(ctx);
    }
}
