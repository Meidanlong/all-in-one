package com.mdl.springai.mcp.client.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 聊天与TTS业务工厂
 *
 * @author meidanlong
 * @date 2025年08月07日
 * @version: 1.0
 */
@Component
public class ChatTtsBizFactory {

    @Value("${volcano.tts.use-websocket:false}")
    private boolean useWebSocket;

    private final ChatTtsBiz chatTtsBiz;
    private final ChatWebSocketTtsBiz chatWebSocketTtsBiz;

    @Autowired
    public ChatTtsBizFactory(ChatTtsBiz chatTtsBiz, ChatWebSocketTtsBiz chatWebSocketTtsBiz) {
        this.chatTtsBiz = chatTtsBiz;
        this.chatWebSocketTtsBiz = chatWebSocketTtsBiz;
    }

    /**
     * 获取聊天与TTS业务实现
     *
     * @return 聊天与TTS业务实现
     */
    public IChatTtsBiz getChatTtsBiz() {
        return useWebSocket ? chatWebSocketTtsBiz : chatTtsBiz;
    }

    /**
     * 获取聊天与TTS业务实现
     *
     * @param useWebSocket 是否使用WebSocket
     * @return 聊天与TTS业务实现
     */
    public IChatTtsBiz getChatTtsBiz(boolean useWebSocket) {
        return useWebSocket ? chatWebSocketTtsBiz : chatTtsBiz;
    }

    /**
     * 获取HTTP聊天与TTS业务实现
     *
     * @return HTTP聊天与TTS业务实现
     */
    public ChatTtsBiz getHttpChatTtsBiz() {
        return chatTtsBiz;
    }

    /**
     * 获取WebSocket聊天与TTS业务实现
     *
     * @return WebSocket聊天与TTS业务实现
     */
    public ChatWebSocketTtsBiz getWebSocketChatTtsBiz() {
        return chatWebSocketTtsBiz;
    }
}

