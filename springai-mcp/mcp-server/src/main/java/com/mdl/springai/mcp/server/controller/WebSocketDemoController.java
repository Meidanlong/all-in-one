package com.mdl.springai.mcp.server.controller;

import com.mdl.springai.mcp.server.service.WebSocketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebSocket演示控制器
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
@RestController
@RequestMapping("/demo/ws")
public class WebSocketDemoController {

    private final WebSocketService webSocketService;

    public WebSocketDemoController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    /**
     * 广播消息
     *
     * @param message 消息内容
     * @return 连接数
     */
    @GetMapping("/broadcast")
    public String broadcastMessage(@RequestParam String message) {
        webSocketService.broadcastMessage("demo", message);
        return "消息已广播，当前连接数: " + webSocketService.getConnectedCount();
    }

    /**
     * 向指定业务方发送消息
     *
     * @param businessId 业务标识
     * @param message    消息内容
     * @return 连接数
     */
    @GetMapping("/send")
    public String sendMessage(@RequestParam String businessId, @RequestParam String message) {
        webSocketService.sendMessageToBusiness(businessId, message);
        return "消息已发送给业务方: " + businessId;
    }

    /**
     * 获取当前连接数
     *
     * @return 连接数
     */
    @GetMapping("/count")
    public String getConnectedCount() {
        return "当前WebSocket连接数: " + webSocketService.getConnectedCount();
    }
}

