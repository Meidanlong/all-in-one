package com.mdl.springai.mcp.server.websocket;

import com.alibaba.fastjson.JSON;
import com.mdl.springai.mcp.server.domain.TPushMessageReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * MCP WebSocket处理器
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
@Slf4j
@Component
public class McpWebSocketHandler implements WebSocketHandler {

    /**
     * 存储所有活跃的WebSocket会话
     */
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        log.info("WebSocket连接已建立，sessionId: {}", session.getId());

        // 发送连接成功消息
        TPushMessageReq message = new TPushMessageReq();
        message.setBusinessId("system");
        message.setData("WebSocket连接成功");
        sendMessage(session, message);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            String payload = ((TextMessage) message).getPayload();
            log.info("收到WebSocket消息，sessionId: {}, message: {}", session.getId(), payload);

            // 解析消息
            TPushMessageReq request = JSON.parseObject(payload, TPushMessageReq.class);

            // 回复消息
            TPushMessageReq response = new TPushMessageReq();
            response.setBusinessId("system");
            response.setData("收到消息: " + request.getData());
            sendMessage(session, response);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误，sessionId: {}", session.getId(), exception);
        sessions.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session.getId());
        log.info("WebSocket连接已关闭，sessionId: {}, status: {}", session.getId(), closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 向指定会话发送消息
     *
     * @param session WebSocket会话
     * @param message 消息对象
     */
    public void sendMessage(WebSocketSession session, TPushMessageReq message) {
        try {
            if (session.isOpen()) {
                String jsonMessage = JSON.toJSONString(message);
                session.sendMessage(new TextMessage(jsonMessage));
            }
        } catch (Exception e) {
            log.error("发送WebSocket消息失败，sessionId: {}", session.getId(), e);
        }
    }

    /**
     * 向所有连接的客户端广播消息
     *
     * @param message 消息对象
     */
    public void broadcastMessage(TPushMessageReq message) {
        sessions.values().forEach(session -> sendMessage(session, message));
    }

    /**
     * 根据业务ID发送消息
     *
     * @param businessId 业务方标识
     * @param message    消息对象
     */
    public void sendMessageByBusinessId(String businessId, TPushMessageReq message) {
        sessions.values().forEach(session -> {
            try {
                // 这里可以根据业务需求进行过滤
                sendMessage(session, message);
            } catch (Exception e) {
                log.error("向业务方发送WebSocket消息失败，businessId: {}", businessId, e);
            }
        });
    }

    /**
     * 获取当前连接数
     *
     * @return 连接数
     */
    public int getConnectedCount() {
        return sessions.size();
    }
}

