package com.mdl.springai.mcp.client.service.impl;

import com.mdl.springai.mcp.client.service.IChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

/**
 * 聊天服务实现
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
@Slf4j
@Service
public class ChatServiceImpl implements IChatService {

    private final String systemPrompt =
            """
                        你是一个非常聪明的人工智能助手，可以帮我解决很多问题，我为你取一个名字，你的名字叫'LaGoGo'。
                    """;

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultSystem(systemPrompt)
                .build();
    }

    @Override
    public String chat(String prompt) {
        return chatClient.prompt(prompt).call().content();
    }
}
