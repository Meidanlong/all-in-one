package com.mdl.digitalhuman.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdl.digitalhuman.controller.FrontendWebSocket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

/**
 * 腾讯云数智人服务
 * 注意：需替换配置参数为腾讯云官方控制台获取的实际值
 * 接口文档参考：https://cloud.tencent.com/document/product/数字人服务相关文档
 *
 * @author meidanlong
 * @date 2025年11月18日
 * @version: 1.0
 */
@Slf4j
@Data
@Service
public class TencentWebSocketService {
    // 从配置文件读取参数（推荐生产环境使用）
    @Value("${tencent.ws.url:}")
    private String tencentWsUrl;

    @Value("${tencent.auth.token:}")
    private String tencentAuthToken;

    @Value("${tencent.character.id:}")
    private String tencentCharacterId;

    // 音频参数（需与腾讯云接口要求一致）
    private static final int SAMPLE_RATE = 16000;
    private static final int CHANNELS = 1;
    private static final String CODEC = "opus";

    private WebSocketClient tencentClient;
    private FrontendWebSocket frontendClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Base64.Encoder encoder = Base64.getEncoder();

    // 初始化连接
    public void init(FrontendWebSocket frontendClient) {
        this.frontendClient = frontendClient;
        try {
            // 验证配置参数
            validateConfig();
            connect();
        } catch (URISyntaxException e) {
            log.error("腾讯云WebSocket初始化失败：{}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("配置参数错误：{}", e.getMessage());
        }
    }

    // 验证配置参数
    private void validateConfig() {
        if (tencentWsUrl == null || tencentWsUrl.isEmpty()) {
            throw new IllegalArgumentException("腾讯云WebSocket地址未配置");
        }
        if (tencentAuthToken == null || tencentAuthToken.isEmpty()) {
            throw new IllegalArgumentException("腾讯云认证Token未配置");
        }
        if (tencentCharacterId == null || tencentCharacterId.isEmpty()) {
            throw new IllegalArgumentException("数字人角色ID未配置");
        }
    }

    // 建立腾讯云连接
    private void connect() throws URISyntaxException {
        URI uri = new URI(tencentWsUrl);
        tencentClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                log.info("腾讯云数智人WebSocket连接建立，响应状态：{}", handshakedata.getHttpStatusMessage());
                // 发送初始化指令（绑定角色）
                sendInitCommand();
            }

            @Override
            public void onMessage(String message) {
                try {
                    log.info("接收腾讯云消息：{}", message.length() > 100 ? message.substring(0, 100) + "..." : message);
                    String eventType = objectMapper.readTree(message).get("EventType").asText();

                    // 转发动画参数到前端
                    if ("ANIMATION_PARAMS".equals(eventType)) {
                        if (frontendClient != null && frontendClient.isSessionOpen()) {
                            frontendClient.sendToFrontend("animation", message);
                        }
                    } else if ("ERROR".equals(eventType)) {
                        String errorMsg = objectMapper.readTree(message).get("ErrorMsg").asText();
                        log.error("腾讯云接口错误：{}", errorMsg);
                    }
                } catch (Exception e) {
                    log.error("腾讯云消息处理失败：{}", e.getMessage());
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                log.info("腾讯云WebSocket关闭：code={}, reason={}, remote={}", code, reason, remote);
                // 连接关闭时尝试重连
                if (code != 1000) { // 非正常关闭
                    try {
                        Thread.sleep(3000);
                        connect();
                    } catch (Exception e) {
                        log.error("重连失败：{}", e.getMessage());
                    }
                }
            }

            @Override
            public void onError(Exception ex) {
                log.error("腾讯云WebSocket错误：{}", ex.getMessage());
            }
        };

        // 设置鉴权头
        tencentClient.addHeader("Authorization", "Bearer " + tencentAuthToken);
        tencentClient.addHeader("Content-Type", "application/json");
        tencentClient.connect();
        log.info("腾讯云数智人WebSocket连接中...");
    }

    // 发送初始化指令
    private void sendInitCommand() {
        try {
            String initMsg = String.format(
                    "{\"Command\":\"INIT_CHARACTER\",\"CharacterParam\":{\"CharacterId\":\"%s\",\"SampleRate\":%d,\"Channel\":%d}}",
                    tencentCharacterId, SAMPLE_RATE, CHANNELS
            );
            tencentClient.send(initMsg);
            log.info("腾讯云初始化指令发送：{}", initMsg);
        } catch (Exception e) {
            log.error("腾讯云初始化指令发送失败：{}", e.getMessage());
        }
    }

    // 接收音频并转发到腾讯云
    public void sendAudioToTencent(String audioBase64) {
        if (tencentClient == null || !tencentClient.isOpen()) {
            log.error("腾讯云连接未建立，无法转发音频");
            return;
        }

        try {
            // 解码音频
            byte[] audioData = Base64.getDecoder().decode(audioBase64);

            // 分帧处理（20ms/帧）
            int frameSize = 640; // 16kHz、16bit、单声道：640字节/20ms
            int offset = 0;
            while (offset < audioData.length) {
                int length = Math.min(frameSize, audioData.length - offset);
                byte[] frame = new byte[length];
                System.arraycopy(audioData, offset, frame, 0, length);
                offset += length;

                // 构造音频帧消息
                String audioFrameBase64 = encoder.encodeToString(frame);
                String sendMsg = String.format(
                        "{\"Header\":{\"Command\":\"SEND_AUDIO\"},\"Payload\":{\"AudioData\":\"%s\",\"SampleRate\":%d,\"Channel\":%d,\"Codec\":\"%s\",\"IsLastFrame\":%b}}",
                        audioFrameBase64, SAMPLE_RATE, CHANNELS, CODEC, offset >= audioData.length
                );
                tencentClient.send(sendMsg);
            }
            log.info("音频分帧转发完成，总长度：{}字节，总帧数：{}", audioData.length, (audioData.length + frameSize - 1) / frameSize);
        } catch (Exception e) {
            log.error("音频转发到腾讯云失败：{}", e.getMessage());
        }
    }

    // 关闭连接
    public void close() {
        if (tencentClient != null && tencentClient.isOpen()) {
            tencentClient.close(1000, "正常关闭");
        }
    }

    // 设置前端客户端
    public void setFrontendClient(FrontendWebSocket frontendClient) {
        this.frontendClient = frontendClient;
    }
}