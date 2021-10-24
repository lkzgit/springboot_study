package com.demo.chat.ws;

import com.demo.chat.pojo.Message;
import com.demo.chat.utils.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version v1.0
 * @ClassName: ChatEndpoint
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {

    //用来存储每一个客户端对象对应的ChatEndpoint对象
    private static Map<String,ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    //声明session对象，通过该对象可以发送消息给指定的用户
    private Session session;

    //声明一个HttpSession对象，我们之前在HttpSession对象中存储了用户名
    private HttpSession httpSession;

    @OnOpen
    //连接建立时被调用
    public void onOpen(Session session, EndpointConfig config) {
        //将局部的session对象赋值给成员session
        this.session = session;
        //获取httpSession对象
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;

        //从httpSession对象中获取用户名
        String username = (String) httpSession.getAttribute("user");

        //将当前对象存储到容器中
        onlineUsers.put(username,this);

        //将当前在线用户的用户名推送给所有的客户端
        //1,获取消息
        String message = MessageUtils.getMessage(true, null, getNames());
        //2,调用方法进行系统消息的推送
        broadcastAllUsers(message);
    }

    private void broadcastAllUsers(String message) {
        try {
            //要将该消息推送给所有的客户端
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                ChatEndpoint chatEndpoint = onlineUsers.get(name);
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<String> getNames() {
        return onlineUsers.keySet();
    }

    @OnMessage
    //接收到客户端发送的数据时被调用
    public void onMessage(String message, Session session) {
        try {
            //将message转换成message对象
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            //获取要将数据发送给的用户
            String toName = mess.getToName();
            //获取消息数据
            String data = mess.getMessage();
            //获取当前登陆的用户
            String username = (String) httpSession.getAttribute("user");
            //获取推送给指定用户的消息格式的数据
            String resultMessage = MessageUtils.getMessage(false, username, data);
            //发送数据
            onlineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    //连接关闭时被调用
    public void onClose(Session session) {

        String username = (String) httpSession.getAttribute("user");
        //从容器中删除指定的用户
        onlineUsers.remove(username);
        //获取推送的消息
        String message = MessageUtils.getMessage(true, null, getNames());
        broadcastAllUsers(message);
    }
}
