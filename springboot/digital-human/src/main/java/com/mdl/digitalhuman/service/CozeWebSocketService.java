package com.mdl.digitalhuman.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdl.digitalhuman.controller.FrontendWebSocket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Coze WebSocket服务
 * 注意：需替换配置参数为Coze官方获取的实际值
 * 接口文档参考：https://www.coze.cn/docs/developer
 *
 * @author meidanlong
 * @date 2025年11月18日
 * @version: 1.0
 */
@Slf4j
@Data
@Service
public class CozeWebSocketService {
    // 从配置文件读取参数
    @Value("${coze.ws.url:}")
    private String cozeWsUrl;

    @Value("${coze.auth.token:}")
    private String cozeAuthToken;

    @Resource
    private TencentWebSocketService tencentWebSocketService;

    // 音频参数（需与Coze接口要求一致）
    private static final int SAMPLE_RATE = 16000;
    private static final int CHANNELS = 1;
    private static final String AUDIO_FORMAT = "opus";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Base64.Decoder decoder = Base64.getDecoder();
    private WebSocketClient cozeClient;
    private FrontendWebSocket frontendClient;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    // 初始化连接
    public void init(FrontendWebSocket frontendClient) {
        this.frontendClient = frontendClient;
        try {
            validateConfig();
            connectWithRetry(3);
        } catch (IllegalArgumentException e) {
            log.error("配置参数错误：{}", e.getMessage());
        }
    }

    // 验证配置参数
    private void validateConfig() {
        if (cozeWsUrl == null || cozeWsUrl.isEmpty()) {
            throw new IllegalArgumentException("Coze WebSocket地址未配置");
        }
        if (cozeAuthToken == null || cozeAuthToken.isEmpty()) {
            throw new IllegalArgumentException("Coze认证Token未配置");
        }
    }

    // 带重试的连接机制
    private void connectWithRetry(int retryCount) {
        try {
            connect();
        } catch (URISyntaxException e) {
            log.error("Coze WebSocket连接失败，剩余重试次数: {}", retryCount, e);
            if (retryCount > 0) {
                scheduler.schedule(() -> connectWithRetry(retryCount - 1), 5, TimeUnit.SECONDS);
            } else {
                log.error("达到最大重试次数，连接失败");
            }
        }
    }

    // 建立Coze连接
    private void connect() throws URISyntaxException {
        URI uri = new URI(cozeWsUrl);
        cozeClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                log.info("Coze WebSocket连接建立，响应状态：{}", handshakedata.getHttpStatusMessage());
                // 发送初始化指令
                sendInitCommand();
            }

            @Override
            public void onMessage(String message) {
                try {
                    log.info("接收Coze消息：{}", message.length() > 100 ? message.substring(0, 100) + "..." : message);
                    String eventType = parseEventType(message);

                    switch (eventType) {
                        case "CONVERSATION_AUDIO_DELTA":
                            handleAudioDelta(message);
                            break;
                        case "CONVERSATION_END":
                            log.info("Coze对话结束");
                            // 通知前端对话结束
                            if (frontendClient != null && frontendClient.isSessionOpen()) {
                                frontendClient.sendToFrontend("conversation", "{\"status\":\"end\"}");
                            }
                            break;
                        case "ERROR":
                            String errorMsg = objectMapper.readTree(message).get("message").asText();
                            log.error("Coze接口错误：{}", errorMsg);
                            // 向前端推送错误信息
                            if (frontendClient != null && frontendClient.isSessionOpen()) {
                                frontendClient.sendToFrontend("error", "{\"source\":\"coze\",\"message\":\"" + errorMsg + "\"}");
                            }
                            break;
                        default:
                            log.debug("未处理的Coze消息类型：{}", eventType);
                    }
                } catch (Exception e) {
                    log.error("Coze消息处理失败：{}", e.getMessage());
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                log.info("Coze WebSocket关闭：code={}, reason={}, remote={}", code, reason, remote);
                // 非正常关闭时尝试重连
                if (code != 1000) {
                    scheduler.schedule(() -> connectWithRetry(3), 3, TimeUnit.SECONDS);
                }
            }

            @Override
            public void onError(Exception ex) {
                log.error("Coze WebSocket错误：{}", ex.getMessage());
            }
        };

        // 设置鉴权头
        cozeClient.addHeader("Authorization", "Bearer " + cozeAuthToken);
        cozeClient.addHeader("Content-Type", "application/json");
        cozeClient.connect();
        log.info("Coze WebSocket连接中...");
    }

    // 处理音频流
    private void handleAudioDelta(String message) throws Exception {
        String audioBase64 = parseAudioData(message);
        // 1. 向前端推送音频数据（播放）
        if (frontendClient != null && frontendClient.isSessionOpen()) {
            frontendClient.sendToFrontend("audio", "\"" + audioBase64 + "\"");
        }
        // 2. 转发音频到腾讯云驱动数字人
        tencentWebSocketService.sendAudioToTencent(audioBase64);
    }

    // 发送初始化指令
    private void sendInitCommand() {
        try {
            Map<String, Object> initRequest = new HashMap<>();
            initRequest.put("action", "start_conversation");
            initRequest.put("version", "1.0");

            Map<String, Object> audioConfig = new HashMap<>();
            audioConfig.put("format", AUDIO_FORMAT);
            audioConfig.put("sample_rate", SAMPLE_RATE);
            audioConfig.put("channels", CHANNELS);

            initRequest.put("audio_config", audioConfig);
            // 添加会话ID（可选，用于会话续传）
            initRequest.put("conversation_id", System.currentTimeMillis());

            String initMsg = objectMapper.writeValueAsString(initRequest);
            cozeClient.send(initMsg);
            log.info("Coze初始化指令发送：{}", initMsg);
        } catch (Exception e) {
            log.error("Coze初始化指令发送失败：{}", e.getMessage());
        }
    }

    // 发送用户音频分片
    public void sendUserAudio(String audioBase64) {
        if (cozeClient != null && cozeClient.isOpen()) {
            try {
                Map<String, Object> audioMsg = new HashMap<>();
                audioMsg.put("type", "user_audio_chunk");
                audioMsg.put("data", audioBase64);
                audioMsg.put("is_last", false);
                audioMsg.put("timestamp", System.currentTimeMillis());

                cozeClient.send(objectMapper.writeValueAsString(audioMsg));
                log.info("用户语音分片转发到Coze，数据长度：{}", audioBase64.length());
            } catch (Exception e) {
                log.error("发送用户音频失败：{}", e.getMessage());
            }
        } else {
            log.error("Coze连接未建立，无法转发用户语音");
        }
    }

    // 发送音频结束标识
    public void sendUserAudioEnd() {
        if (cozeClient != null && cozeClient.isOpen()) {
            try {
                Map<String, Object> endMsg = new HashMap<>();
                endMsg.put("type", "user_audio_chunk");
                endMsg.put("data", "");
                endMsg.put("is_last", true);
                endMsg.put("timestamp", System.currentTimeMillis());

                cozeClient.send(objectMapper.writeValueAsString(endMsg));
                log.info("用户语音发送结束标识已发送");
            } catch (Exception e) {
                log.error("发送音频结束标识失败：{}", e.getMessage());
            }
        }
    }

    // 解析消息类型
    private String parseEventType(String message) throws Exception {
        return objectMapper.readTree(message).get("type").asText();
    }

    // 解析音频数据
    private String parseAudioData(String message) throws Exception {
        return objectMapper.readTree(message).get("data").get("audio").asText();
    }

    // 关闭连接
    public void close() {
        if (cozeClient != null && cozeClient.isOpen()) {
            cozeClient.close(1000, "正常关闭");
        }
        scheduler.shutdown();
    }

    // 设置前端客户端
    public void setFrontendClient(FrontendWebSocket frontendClient) {
        this.frontendClient = frontendClient;
    }
}