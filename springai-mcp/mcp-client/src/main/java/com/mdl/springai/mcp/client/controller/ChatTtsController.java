package com.mdl.springai.mcp.client.controller;

import com.mdl.springai.mcp.client.biz.ChatTtsBizFactory;
import com.mdl.springai.mcp.client.biz.IChatTtsBiz;
import com.mdl.springai.mcp.client.domain.dto.ChatRequestDTO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 聊天与TTS控制层
 *
 * @author meidanlong
 * @date 2025年08月07日
 * @version: 1.0
 */
@RestController
@RequestMapping("api/llm/chat-tts")
public class ChatTtsController {

    @Resource
    private ChatTtsBizFactory chatTtsBizFactory;

    /**
     * 聊天并播放语音
     *
     * @param prompt 提示词
     * @param useWebSocket 是否使用WebSocket
     * @return 聊天响应
     */
    @GetMapping
    public String chatWithVoice(@RequestParam("prompt") String prompt,
                                @RequestParam(value = "useWebSocket", required = false) Boolean useWebSocket) {
        IChatTtsBiz chatTtsBiz = chatTtsBizFactory.getChatTtsBiz(Boolean.TRUE.equals(useWebSocket));
        return chatTtsBiz.chatWithVoice(prompt);
    }

    /**
     * 流式聊天并播放语音
     *
     * @param prompt 提示词
     * @param useWebSocket 是否使用WebSocket
     * @return 聊天响应流
     */
    @GetMapping("stream")
    public Flux<String> chatStreamWithVoice(@RequestParam("prompt") String prompt,
                                           @RequestParam(value = "useWebSocket", required = false) Boolean useWebSocket) {
        IChatTtsBiz chatTtsBiz = chatTtsBizFactory.getChatTtsBiz(Boolean.TRUE.equals(useWebSocket));
        return chatTtsBiz.chatStreamWithVoice(prompt);
    }

    /**
     * SSE聊天并播放语音
     *
     * @param chatRequestDTO 聊天请求
     * @param useWebSocket 是否使用WebSocket
     */
    @PostMapping("sse")
    public void chatSSEWithVoice(@RequestBody ChatRequestDTO chatRequestDTO,
                                @RequestParam(value = "useWebSocket", required = false) Boolean useWebSocket) {
        IChatTtsBiz chatTtsBiz = chatTtsBizFactory.getChatTtsBiz(Boolean.TRUE.equals(useWebSocket));
        chatTtsBiz.chatSSEWithVoice(chatRequestDTO);
    }
}

