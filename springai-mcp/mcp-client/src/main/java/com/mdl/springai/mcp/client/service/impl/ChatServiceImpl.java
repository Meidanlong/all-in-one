package com.mdl.springai.mcp.client.service.impl;

import cn.hutool.json.JSONUtil;
import com.mdl.springai.mcp.client.domain.dto.ChatRequestDTO;
import com.mdl.springai.mcp.client.domain.dto.ChatResponseDTO;
import com.mdl.springai.mcp.client.domain.enums.SSEMsgTypeEnum;
import com.mdl.springai.mcp.client.service.IChatService;
import com.mdl.springai.mcp.client.utils.SSEServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

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
                    你是一个非常聪明的人工智能助手，可以帮我解决很多问题，我为你取一个名字，你的名字叫'一休'。
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

    @Override
    public Flux<String> chatStream(String prompt) {
        return chatClient.prompt(prompt).stream().content();
    }

    @Override
    public void chatSSE(ChatRequestDTO chatRequestDTO) {

        String userId = chatRequestDTO.getCurrentUserName();
        String prompt = chatRequestDTO.getMessage();
        String botMsgId = chatRequestDTO.getBotMsgId();

        Flux<String> stringFlux = chatClient.prompt(prompt).stream().content();

        List<String> list = stringFlux.toStream().map(chatResponse -> {
            String content = chatResponse;
            SSEServer.sendMsg(userId, content, SSEMsgTypeEnum.ADD);
            log.info("content: {}", content);
            return content;
        }).collect(Collectors.toList());

        String fullContent = list.stream().collect(Collectors.joining());

        ChatResponseDTO chatResponseDTO = new ChatResponseDTO(fullContent, botMsgId);

        SSEServer.sendMsg(userId, JSONUtil.toJsonStr(chatResponseDTO), SSEMsgTypeEnum.FINISH);

    }
}
