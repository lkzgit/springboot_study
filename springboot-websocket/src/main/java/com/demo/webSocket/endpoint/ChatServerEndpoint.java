package com.demo.webSocket.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 说明：
 * 1、@ServerEndpoint注解中指定WebSocket协议的地址；
 * 2、@OnOpen、@OnMessage、@OnClose、@OnError注解与WebSocket中监听事件对应
 *
 **/
@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class ChatServerEndpoint {

    /**
     * 连接建立时触发
     */
    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        log.info("用户{}登录", username);
        String message = "用户[" + username + "] 已进入聊天室！";
        // 发送登录消息给其他人
        WebSocketUtils.sendMessageAll(message);

        // 获取当前在线人数，发给自己
        String onlineInfo = WebSocketUtils.getOnlineInfo();

        //发送消息
        WebSocketUtils.sendMessage(session, onlineInfo);

        // 添加自己到map中
        WebSocketUtils.CLIENTS.put(username, session);
    }

    /**
     * 客户端接收服务端数据时触发
     */
    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {
        log.info("发送消息：{}, {}", username, message);
        //广播，把消息同步给其他客户端
        WebSocketUtils.sendMessageAll("[" + username + "] : " + message);
    }

    /**
     * 连接关闭时触发
     */
    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {
        // 当前的Session移除某个用户
        WebSocketUtils.CLIENTS.remove(username);

        // 离开消息通知所有人
        WebSocketUtils.sendMessageAll("[" + username + "] 已离开！");

        try {
            //关闭WebSocket Session会话
            session.close();
            log.info("{} 已退出, onclose", username);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("onClose error", e);
        }
    }

    /**
     * 通信发生错误时触发
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            //关闭WebSocket Session会话
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("onError Exception", e);
        }
        log.info("Throwable msg " + throwable.getMessage());
    }
}