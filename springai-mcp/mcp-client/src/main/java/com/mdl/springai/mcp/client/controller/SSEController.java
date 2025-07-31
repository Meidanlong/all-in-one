package com.mdl.springai.mcp.client.controller;

import com.mdl.springai.mcp.client.domain.enums.SSEMsgTypeEnum;
import com.mdl.springai.mcp.client.utils.SSEServer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * SSE控制层
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
@RestController
@RequestMapping("api/llm/sse")
public class SSEController {

    /**
     * @param
     * @return String
     * @Description: 前端发送连接的请求，连接SSE服务
     * @Author meidanlong
     */
    @GetMapping(path = "connect", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter connect(@RequestParam("userId") String userId) {
        return SSEServer.connect(userId);
    }

    /**
     * @param
     * @return String
     * @Description: SSE发送单个消息
     * @Author meidanlong
     */
    @GetMapping("sendMessage")
    public Object sendMessage(@RequestParam("userId") String userId,
                              @RequestParam("message") String message) {
        SSEServer.sendMsg(userId, message, SSEMsgTypeEnum.MESSAGE);
        return "OK";
    }

    /**
     * @param userId
     * @param message
     * @return Object
     * @Description: SSE发送单个消息 - add
     * @Author meidanlong
     */
    @GetMapping("sendMessageAdd")
    public Object sendMessageAdd(@RequestParam("userId") String userId,
                                 @RequestParam("message") String message) throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(200);
            SSEServer.sendMsg(userId, message, SSEMsgTypeEnum.ADD);
        }
        return "OK";
    }

    /**
     * @param message
     * @return Object
     * @Description: SSE发送群消息
     * @Author meidanlong
     */
    @GetMapping("sendMessageAll")
    public Object sendMessageAll(@RequestParam("message") String message) {
        SSEServer.sendMsgToAllUsers(message);
        return "OK";
    }

}
