package com.mdl.digitalhuman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置
 *
 * @author meidanlong
 * @date 2025年11月18日
 * @version: 1.0
 */
@Configuration
public class WebSocketConfig {
    // 开启WebSocket支持
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    // 配置WebSocket会话管理器
    @Bean
    public WebSocketSpringConfigurator customSpringConfigurator() {
        return new WebSocketSpringConfigurator();
    }
}
