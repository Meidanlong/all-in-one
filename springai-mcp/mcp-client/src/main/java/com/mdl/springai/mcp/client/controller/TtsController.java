package com.mdl.springai.mcp.client.controller;

import com.mdl.springai.mcp.client.service.ITtsService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * TTS控制层
 *
 * @author meidanlong
 * @date 2025年08月06日
 * @version: 1.0
 */
@RestController
@RequestMapping("api/llm/tts")
public class TtsController {

    @Resource
    private ITtsService ttsService;

    /**
     * 文本转语音
     *
     * @param text 文本
     * @return 语音数据
     */
    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] textToSpeech(@RequestParam("text") String text) {
        return ttsService.textToSpeech(text);
    }

    /**
     * 文本转语音流
     *
     * @param text 文本
     * @return 语音数据流
     */
    @GetMapping(path = "stream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Flux<byte[]> textToSpeechStream(@RequestParam("text") String text) {
        return ttsService.textToSpeechStream(text);
    }

    /**
     * 播放文本
     *
     * @param text 文本
     * @return 结果
     */
    @PostMapping("play")
    public String playText(@RequestParam("text") String text) {
        ttsService.playText(text);
        return "播放成功";
    }
}

