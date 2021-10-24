package com.demo.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @version v1.0
 * @ClassName: WebSocketConfig
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 黑马程序员
 */
@Configuration
public class WebSocketConfig {

    @Bean
    //注入ServerEndpointExporter bean对象，自动注册使用了@ServerEndpoint注解的bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
