package com.mdl.springai.mcp.server.controller;

import com.mdl.springai.mcp.server.service.BusinessService;
import com.mdl.springai.mcp.server.service.WebSocketService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 业务控制器示例
 * 演示如何在HTTP接口中动态发送WebSocket消息
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;
    private final WebSocketService webSocketService;

    public BusinessController(BusinessService businessService, WebSocketService webSocketService) {
        this.businessService = businessService;
        this.webSocketService = webSocketService;
    }

    /**
     * 启动任务处理并通知客户端
     *
     * @param taskId 任务ID
     * @return 响应信息
     */
    @PostMapping("/process/{taskId}")
    public String startProcessing(@PathVariable String taskId) {
        // 启动异步任务处理
        new Thread(() -> businessService.processTask(taskId)).start();

        // 立即通过WebSocket通知客户端任务已启动
        webSocketService.sendMessageToBusiness("task-" + taskId, "任务已启动，正在处理中...");

        return "任务已启动，任务ID: " + taskId;
    }

    /**
     * 发送系统通知
     *
     * @param message 通知内容
     * @return 响应信息
     */
    @PostMapping("/notification")
    public String sendNotification(@RequestParam String message) {
        businessService.broadcastNotification(message);
        return "通知已发送: " + message;
    }

    /**
     * 向特定客户端发送消息
     *
     * @param clientId 客户端ID
     * @param message  消息内容
     * @return 响应信息
     */
    @PostMapping("/message/{clientId}")
    public String sendMessageToClient(@PathVariable String clientId, @RequestParam String message) {
        webSocketService.sendMessageToBusiness("client-" + clientId, message);
        return "消息已发送到客户端: " + clientId;
    }
}

