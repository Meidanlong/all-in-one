package com.mdl.springai.mcp.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 业务服务示例类
 * 演示如何在业务逻辑中动态发送WebSocket消息
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
@Slf4j
@Service
public class BusinessService {

    private final WebSocketService webSocketService;

    public BusinessService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    /**
     * 模拟业务处理过程，动态向客户端发送进度消息
     *
     * @param taskId 任务ID
     */
    public void processTask(String taskId) {
        try {
            // 任务开始
            sendProgressMessage(taskId, "任务开始处理...");

            // 模拟处理步骤1
            Thread.sleep(1000);
            sendProgressMessage(taskId, "步骤1完成...");

            // 模拟处理步骤2
            Thread.sleep(1000);
            sendProgressMessage(taskId, "步骤2完成...");

            // 模拟处理步骤3
            Thread.sleep(1000);
            sendProgressMessage(taskId, "步骤3完成...");

            // 任务完成
            sendProgressMessage(taskId, "任务处理完成!");
        } catch (Exception e) {
            log.error("处理任务时发生错误, taskId: {}", taskId, e);
            sendProgressMessage(taskId, "任务处理失败: " + e.getMessage());
        }
    }

    /**
     * 发送进度消息到客户端
     *
     * @param taskId  任务ID
     * @param message 消息内容
     */
    private void sendProgressMessage(String taskId, String message) {
        // 构造业务标识，可以包含任务ID等信息
        String businessId = "task-" + taskId;

        // 发送消息到指定业务方
        webSocketService.sendMessageToBusiness(businessId, message);

        log.info("发送进度消息, taskId: {}, message: {}", taskId, message);
    }

    /**
     * 广播系统通知
     *
     * @param notification 通知内容
     */
    public void broadcastNotification(String notification) {
        webSocketService.broadcastMessage("system-notification", notification);
        log.info("广播系统通知: {}", notification);
    }
}

