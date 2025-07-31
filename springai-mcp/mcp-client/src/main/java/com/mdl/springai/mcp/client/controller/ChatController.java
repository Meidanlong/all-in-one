package com.mdl.springai.mcp.client.controller;

import com.mdl.springai.mcp.client.domain.dto.ChatRequestDTO;
import com.mdl.springai.mcp.client.service.IChatService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 聊天控制层
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
@RestController
@RequestMapping("api/llm/chat")
public class ChatController {

    @Resource
    private IChatService chatService;


    @GetMapping
    public String chat(@RequestParam("prompt") String prompt) {
        return chatService.chat(prompt);
    }

    @GetMapping("stream")
    public Flux<String> chatStream(@RequestParam("prompt") String prompt) {
        return chatService.chatStream(prompt);
    }

    @PostMapping("sse")
    public void doChat(@RequestBody ChatRequestDTO chatRequestDTO) {
        chatService.chatSSE(chatRequestDTO);
    }


}
