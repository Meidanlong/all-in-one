package com.mdl.springai.mcp.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TTS服务工厂
 *
 * @author meidanlong
 * @date 2025年08月06日
 * @version: 1.0
 */
@Component
public class TtsServiceFactory {

    @Value("${volcano.tts.use-websocket:false}")
    private boolean useWebSocket;

    private final ITtsService httpTtsService;
    private final ITtsService webSocketTtsService;

    @Autowired
    public TtsServiceFactory(
            @Autowired(required = false) ITtsService customTtsServiceImpl,
            @Autowired(required = false) ITtsService webSocketTtsServiceImpl) {
        this.httpTtsService = customTtsServiceImpl;
        this.webSocketTtsService = webSocketTtsServiceImpl;
    }

    /**
     * 获取TTS服务
     *
     * @return TTS服务
     */
    public ITtsService getTtsService() {
        return useWebSocket ? webSocketTtsService : httpTtsService;
    }
}

