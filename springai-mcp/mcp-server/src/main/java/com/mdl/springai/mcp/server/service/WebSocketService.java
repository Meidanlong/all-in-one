package com.mdl.springai.mcp.server.service;

import com.mdl.springai.mcp.server.domain.TPushMessageReq;
import com.mdl.springai.mcp.server.websocket.McpWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * WebSocket服务类
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
@Slf4j
@Service
public class WebSocketService {

    private final McpWebSocketHandler webSocketHandler;

    public WebSocketService(McpWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    /**
     * 发送消息到所有连接的客户端
     *
     * @param businessId 业务标识
     * @param data       数据内容
     */
    public void broadcastMessage(String businessId, String data) {
        TPushMessageReq message = new TPushMessageReq();
        message.setBusinessId(businessId);
        message.setData(data);
        webSocketHandler.broadcastMessage(message);
        log.info("广播消息，businessId: {}, data: {}", businessId, data);
    }

    /**
     * 发送消息到指定业务方
     *
     * @param businessId 业务标识
     * @param data       数据内容
     */
    public void sendMessageToBusiness(String businessId, String data) {
        TPushMessageReq message = new TPushMessageReq();
        message.setBusinessId(businessId);
        message.setData(data);
        webSocketHandler.sendMessageByBusinessId(businessId, message);
        log.info("发送消息到业务方，businessId: {}, data: {}", businessId, data);
    }

    /**
     * 获取当前连接数
     *
     * @return 连接数
     */
    public int getConnectedCount() {
        return webSocketHandler.getConnectedCount();
    }
}

