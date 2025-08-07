package com.mdl.springai.mcp.client.biz;

import com.mdl.springai.mcp.client.domain.dto.ChatRequestDTO;
import com.mdl.springai.mcp.client.service.IChatService;
import com.mdl.springai.mcp.client.service.ITtsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * 聊天与WebSocket TTS业务组合层
 *
 * @author meidanlong
 * @date 2025年08月07日
 * @version: 1.0
 */
@Slf4j
@Component
public class ChatWebSocketTtsBiz implements IChatTtsBiz {

    private final IChatService chatService;
    private final ITtsService webSocketTtsService;

    @Autowired
    public ChatWebSocketTtsBiz(IChatService chatService, @Qualifier("webSocketTtsServiceImpl") ITtsService webSocketTtsService) {
        this.chatService = chatService;
        this.webSocketTtsService = webSocketTtsService;
    }

    /**
     * 聊天并通过WebSocket播放语音
     *
     * @param prompt 提示词
     * @return 聊天响应
     */
    @Override
    public String chatWithVoice(String prompt) {
        String response = chatService.chat(prompt);
        // 将文本转换为语音并播放
        webSocketTtsService.playText(response);
        return response;
    }

    /**
     * 流式聊天并通过WebSocket播放语音
     *
     * @param prompt 提示词
     * @return 聊天响应流
     */
    @Override
    public Flux<String> chatStreamWithVoice(String prompt) {
        Flux<String> responseFlux = chatService.chatStream(prompt);
        // 将文本流转换为语音流并播放
        webSocketTtsService.playTextStream(responseFlux);
        return responseFlux;
    }

    /**
     * SSE聊天并通过WebSocket播放语音
     *
     * @param chatRequestDTO 聊天请求
     */
    @Override
    public void chatSSEWithVoice(ChatRequestDTO chatRequestDTO) {
        // 获取原始文本流
        Flux<String> textFlux = chatService.chatStream(chatRequestDTO.getMessage());

        // 将文本流转换为语音流并播放
        webSocketTtsService.playTextStream(textFlux);

        // 执行原始的SSE聊天
        chatService.chatSSE(chatRequestDTO);
    }
}

