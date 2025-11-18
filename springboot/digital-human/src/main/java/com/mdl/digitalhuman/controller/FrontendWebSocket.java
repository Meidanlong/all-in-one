package com.mdl.digitalhuman.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdl.digitalhuman.config.WebSocketSpringConfigurator;
import com.mdl.digitalhuman.service.CozeWebSocketService;
import com.mdl.digitalhuman.service.TencentWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 前端WebSocket控制器
 *
 * @author meidanlong
 * @date 2025年11月18日
 * @version: 1.0
 */
@Slf4j
@Component
@ServerEndpoint(value = "/ws/frontend", configurator = WebSocketSpringConfigurator.class)
public class FrontendWebSocket {
    // 存储前端连接（key:连接ID，value:连接对象）
    private static final ConcurrentHashMap<String, FrontendWebSocket> CONNECTIONS = new ConcurrentHashMap<>();
    private Session session;
    private String connectionId;

    // 注入第三方WebSocket服务（静态注入）
    private static CozeWebSocketService cozeService;
    private static TencentWebSocketService tencentService;

    @Autowired
    public void setCozeService(CozeWebSocketService cozeService) {
        FrontendWebSocket.cozeService = cozeService;
    }

    @Autowired
    public void setTencentService(TencentWebSocketService tencentService) {
        FrontendWebSocket.tencentService = tencentService;
    }

    // 连接建立时触发
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.connectionId = session.getId();
        CONNECTIONS.put(connectionId, this);
        log.info("前端WebSocket连接建立：{}，当前连接数：{}", connectionId, CONNECTIONS.size());

        // 初始化第三方服务（每个连接独立管理）
        cozeService.init(this);
        tencentService.init(this);
    }

    // 接收前端消息（用户语音数据）
    @OnMessage
    public void onMessage(String message) {
        try {
            // 检查是否为纯音频数据（Base64字符串）
            if (message.startsWith("data:audio") || message.matches("^[A-Za-z0-9+/=]+$")) {
                // 处理纯音频数据
                String audioBase64 = message;
                if (audioBase64.startsWith("data:audio")) {
                    audioBase64 = audioBase64.split(",")[1];
                }
                cozeService.sendUserAudio(audioBase64);
                return;
            }

            // 处理JSON格式消息
            JsonNode msgNode = new ObjectMapper().readTree(message);
            String type = msgNode.get("type").asText();

            if ("audio_chunk".equals(type)) {
                // 处理正常音频分片
                String audioBase64 = msgNode.get("data").asText();
                cozeService.sendUserAudio(audioBase64);
            } else if ("audio_end".equals(type)) {
                // 处理语音结束信号
                cozeService.sendUserAudioEnd();  // 调用结束方法
                log.info("收到用户语音结束信号，已通知Coze");
            }
        } catch (Exception e) {
            log.error("处理前端消息失败：{}", e.getMessage());
            // 即使解析JSON失败，也尝试作为纯音频数据处理
            if (message.matches("^[A-Za-z0-9+/=]+$")) {
                try {
                    cozeService.sendUserAudio(message);
                } catch (Exception ex) {
                    log.error("处理纯音频数据失败：{}", ex.getMessage());
                }
            }
        }
    }

    // 连接关闭时触发
    @OnClose
    public void onClose() {
        CONNECTIONS.remove(connectionId);
        log.info("前端WebSocket连接关闭：{}，当前连接数：{}", connectionId, CONNECTIONS.size());
        // 关闭第三方连接
        cozeService.close();
        tencentService.close();
    }

    // 发生错误时触发
    @OnError
    public void onError(Throwable error) {
        log.error("前端WebSocket错误：{}", error.getMessage());
        error.printStackTrace();
    }

    // 向前端推送消息（音频/动画参数）
    public void sendToFrontend(String type, Object data) {
        try {
            String msg = String.format("{\"type\":\"%s\",\"data\":%s}", type, data);
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(msg);
            }
        } catch (IOException e) {
            log.error("向前端推送消息失败：{}", e.getMessage());
        }
    }

    // 检查连接是否有效
    public boolean isSessionOpen() {
        return session != null && session.isOpen();
    }
}
