package com.mdl.springai.mcp.client.biz;

import com.mdl.springai.mcp.client.domain.dto.ChatRequestDTO;
import reactor.core.publisher.Flux;

/**
 * 聊天与TTS业务接口
 *
 * @author meidanlong
 * @date 2025年08月07日
 * @version: 1.0
 */
public interface IChatTtsBiz {

    /**
     * 聊天并播放语音
     *
     * @param prompt 提示词
     * @return 聊天响应
     */
    String chatWithVoice(String prompt);

    /**
     * 流式聊天并播放语音
     *
     * @param prompt 提示词
     * @return 聊天响应流
     */
    Flux<String> chatStreamWithVoice(String prompt);

    /**
     * SSE聊天并播放语音
     *
     * @param chatRequestDTO 聊天请求
     */
    void chatSSEWithVoice(ChatRequestDTO chatRequestDTO);
}

