package com.mdl.springai.mcp.client.service;

import reactor.core.publisher.Flux;

/**
 * TTS服务接口
 *
 * @author meidanlong
 * @date 2025年08月06日
 * @version: 1.0
 */
public interface ITtsService {

    /**
     * 文本转语音
     *
     * @param text 文本
     * @return 语音数据
     */
    byte[] textToSpeech(String text);

    /**
     * 文本转语音流
     *
     * @param text 文本
     * @return 语音数据流
     */
    Flux<byte[]> textToSpeechStream(String text);

    /**
     * 播放文本
     *
     * @param text 文本
     */
    void playText(String text);

    /**
     * 播放文本流
     *
     * @param textFlux 文本流
     */
    void playTextStream(Flux<String> textFlux);
}

