package com.demo.webSocket.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class WebSocketUtils {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketUtils.class);

    /**
     * 存储WebSocket session
     * <p>
     * 用户名为key，WebSocket Session对象为value
     */
    public static final Map<String, Session> CLIENTS = new ConcurrentHashMap<>();

    /**
     * 使用连接发送数据
     *
     * @param session 用户session
     * @param message 发送内容
     */
    public static void sendMessage(Session session, String message) {
        if (session == null) {
            return;
        }
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            return;
        }
        try {
            //发送
            basic.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("sendMessage IOException ", e);
        }
    }

    /**
     * 发送消息给其他所有人
     *
     * @param message
     */
    public static void sendMessageAll(String message) {
        CLIENTS.forEach((sessionId, session) -> sendMessage(session, message));
    }

    /**
     * 获取所有在线用户
     */
    public static String getOnlineInfo() {
        Set<String> userNames = CLIENTS.keySet();
        if (userNames.size() == 0) {
            return "当前无人在线...";
        }
        return CLIENTS.keySet().toString() + "在线";
    }
}