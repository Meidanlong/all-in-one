package com.mdl.springai.mcp.server.controller;

import com.mdl.springai.mcp.server.service.WebSocketService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 动态消息发送控制器
 * 演示各种动态消息发送场景
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/dynamic")
public class DynamicMessageController {

    private final WebSocketService webSocketService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public DynamicMessageController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    /**
     * 实时发送任务进度消息
     *
     * @param taskId 任务ID
     * @return 响应信息
     */
    @PostMapping("/task/{taskId}/progress")
    public String sendTaskProgress(@PathVariable String taskId) {
        // 启动异步任务模拟
        executorService.submit(() -> {
            try {
                // 模拟长时间运行的任务
                for (int i = 1; i <= 10; i++) {
                    Thread.sleep(1000); // 模拟工作
                    String progressMessage = String.format("任务 %s 进度: %d%%", taskId, i * 10);
                    webSocketService.sendMessageToBusiness("task-" + taskId, progressMessage);
                }
                webSocketService.sendMessageToBusiness("task-" + taskId, "任务 " + taskId + " 已完成!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                webSocketService.sendMessageToBusiness("task-" + taskId, "任务 " + taskId + " 被中断!");
            }
        });

        return "任务 " + taskId + " 已启动，进度消息将实时发送";
    }

    /**
     * 发送系统事件消息
     *
     * @param event   事件类型
     * @param message 消息内容
     * @return 响应信息
     */
    @PostMapping("/event/{event}")
    public String sendSystemEvent(@PathVariable String event, @RequestParam String message) {
        webSocketService.broadcastMessage("system-event-" + event, message);
        return "系统事件消息已发送: " + event;
    }

    /**
     * 向多个客户端发送消息
     *
     * @param clientIds 客户端ID列表（逗号分隔）
     * @param message   消息内容
     * @return 响应信息
     */
    @PostMapping("/multi-clients")
    public String sendMessageToMultipleClients(@RequestParam String clientIds, @RequestParam String message) {
        String[] ids = clientIds.split(",");
        for (String clientId : ids) {
            webSocketService.sendMessageToBusiness("client-" + clientId.trim(), message);
        }
        return "消息已发送到 " + ids.length + " 个客户端";
    }

    /**
     * 发送带优先级的消息
     *
     * @param priority 消息优先级
     * @param message  消息内容
     * @return 响应信息
     */
    @PostMapping("/priority/{priority}")
    public String sendPriorityMessage(@PathVariable String priority, @RequestParam String message) {
        String businessId = "priority-" + priority;
        webSocketService.broadcastMessage(businessId, message);
        return "优先级消息已发送: " + priority;
    }
}

