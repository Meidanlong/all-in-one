package com.mdl.springai.mcp.client.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdl.springai.mcp.client.service.ITtsService;
import com.mdl.springai.mcp.client.utils.AudioPlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WebSocket实时语音接口实现 - 使用火山引擎豆包语音合成大模型
 *
 * @author meidanlong
 * @date 2025年08月06日
 * @version: 1.0
 */
@Slf4j
@Service
public class WebSocketTtsServiceImpl implements ITtsService {

    private static final int MAX_TEXT_LENGTH = 500; // 豆包语音合成大模型单次请求最大文本长度
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("[,.!?;，。！？；]");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${volcano.tts.access-key}")
    private String accessKey;

    @Value("${volcano.tts.secret-key}")
    private String secretKey;

    @Value("${volcano.tts.host:open.volcengineapi.com}")
    private String host;

    @Value("${volcano.tts.model:douyin-tts-pro}")
    private String model;

    @Value("${volcano.tts.voice:zh_female_qingxin}")
    private String voice;

    private final ConcurrentHashMap<String, AudioPlayer> playerMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public byte[] textToSpeech(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new byte[0];
        }

        // 创建一个Sink来接收WebSocket的音频数据
        Sinks.Many<byte[]> audioSink = Sinks.many().unicast().onBackpressureBuffer();
        AtomicBoolean completed = new AtomicBoolean(false);

        try {
            // 创建WebSocket连接
            WebSocketSession session = createWebSocketSession(text, audioSink, completed);

            // 等待所有音频数据接收完成
            byte[] combinedAudio = Flux.from(audioSink.asFlux())
                .collectList()
                .map(audioChunks -> {
                    // 计算总长度
                    int totalLength = audioChunks.stream().mapToInt(chunk -> chunk.length).sum();
                    ByteBuffer buffer = ByteBuffer.allocate(totalLength);

                    // 合并所有音频块
                    audioChunks.forEach(buffer::put);

                    return buffer.array();
                })
                .block();

            // 关闭WebSocket连接
            if (session != null && session.isOpen()) {
                session.close();
            }

            return combinedAudio != null ? combinedAudio : new byte[0];
        } catch (Exception e) {
            log.error("Error in WebSocket TTS", e);
            return new byte[0];
        }
    }

    @Override
    public Flux<byte[]> textToSpeechStream(String text) {
        if (text == null || text.trim().isEmpty()) {
            return Flux.empty();
        }

        // 将长文本分割成多个短文本
        return Flux.fromIterable(splitText(text))
                .flatMap(segment -> {
                    // 创建一个Sink来接收WebSocket的音频数据
                    Sinks.Many<byte[]> audioSink = Sinks.many().unicast().onBackpressureBuffer();
                    AtomicBoolean completed = new AtomicBoolean(false);

                    return Mono.fromCallable(() -> {
                        try {
                            // 创建WebSocket连接
                            createWebSocketSession(segment, audioSink, completed);
                            return audioSink.asFlux();
                        } catch (Exception e) {
                            log.error("Error creating WebSocket session", e);
                            return Flux.<byte[]>empty();
                        }
                    })
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMapMany(flux -> flux);
                });
    }

    @Override
    public void playText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return;
        }

        byte[] audioData = textToSpeech(text);
        if (audioData.length > 0) {
            getOrCreatePlayer("default").addAudioData(audioData);
        }
    }

    @Override
    public void playTextStream(Flux<String> textFlux) {
        final String playerId = "stream-" + System.currentTimeMillis();
        final AudioPlayer player = getOrCreatePlayer(playerId);
        final StringBuilder textBuffer = new StringBuilder();
        final AtomicInteger charCount = new AtomicInteger(0);

        textFlux.subscribe(
            text -> {
                textBuffer.append(text);
                charCount.addAndGet(text.length());

                // 当累积的文本达到一定长度或包含句子结束符时，进行TTS转换
                if (shouldProcessBuffer(textBuffer.toString(), charCount.get())) {
                    String textToProcess = textBuffer.toString();
                    textBuffer.setLength(0);
                    charCount.set(0);

                    // 创建一个Sink来接收WebSocket的音频数据
                    Sinks.Many<byte[]> audioSink = Sinks.many().unicast().onBackpressureBuffer();
                    AtomicBoolean completed = new AtomicBoolean(false);

                    try {
                        // 创建WebSocket连接
                        createWebSocketSession(textToProcess, audioSink, completed);

                        // 订阅音频数据并播放
                        audioSink.asFlux().subscribe(
                            audioData -> player.addAudioData(audioData),
                            error -> log.error("Error receiving audio data", error)
                        );
                    } catch (Exception e) {
                        log.error("Error in WebSocket TTS", e);
                    }
                }
            },
            error -> {
                log.error("Error in text stream", error);
                // 处理剩余的文本
                processRemainingText(textBuffer.toString(), player);
            },
            () -> {
                // 处理剩余的文本
                processRemainingText(textBuffer.toString(), player);
            }
        );
    }

    /**
     * 创建WebSocket会话
     *
     * @param text 文本
     * @param audioSink 音频数据接收器
     * @param completed 完成标志
     * @return WebSocket会话
     */
    private WebSocketSession createWebSocketSession(String text, Sinks.Many<byte[]> audioSink, AtomicBoolean completed) throws Exception {
        String sessionId = UUID.randomUUID().toString();
        StandardWebSocketClient client = new StandardWebSocketClient();

        // 构建请求头
        WebSocketHttpHeaders headers = buildWebSocketHeaders(text);

        // 创建WebSocket处理器
        TtsWebSocketHandler handler = new TtsWebSocketHandler(text, audioSink, completed);

        // 连接WebSocket
        WebSocketSession session = client.execute(handler, headers,
            URI.create("wss://" + host + "/api/v1/tts/ws")).get();

        // 保存会话
        sessionMap.put(sessionId, session);

        return session;
    }

    /**
     * 构建WebSocket请求头
     *
     * @param text 文本
     * @return WebSocket请求头
     */
    private WebSocketHttpHeaders buildWebSocketHeaders(String text) throws NoSuchAlgorithmException, InvalidKeyException {
        String path = "/api/v1/tts/ws";
        String method = "GET";
        String contentType = "application/json";
        String date = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME);

        // 构建请求体
        String requestBody = buildRequestBody(text);

        // 计算签名
        String stringToSign = method + "\n" + path + "\n" + date + "\n" + contentType + "\n" + requestBody;
        String signature = calculateSignature(stringToSign, secretKey);

        // 构建授权头
        String authorization = "HMAC-SHA256 AccessKey=" + accessKey + ", Signature=" + signature;

        // 构建请求头
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Date", date);
        headers.add("Authorization", authorization);
        headers.add("X-Date", date);
        headers.add("Host", host);

        return headers;
    }

    /**
     * WebSocket处理器
     */
    private class TtsWebSocketHandler extends AbstractWebSocketHandler {
        private final String text;
        private final Sinks.Many<byte[]> audioSink;
        private final AtomicBoolean completed;

        public TtsWebSocketHandler(String text, Sinks.Many<byte[]> audioSink, AtomicBoolean completed) {
            this.text = text;
            this.audioSink = audioSink;
            this.completed = completed;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            // 发送TTS请求
            String requestBody = buildRequestBody(text);
            session.sendMessage(new TextMessage(requestBody));
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            // 处理文本消息（通常是状态信息）
            String payload = message.getPayload();
            Map<String, Object> response = objectMapper.readValue(payload, Map.class);

            if (response.containsKey("error")) {
                log.error("WebSocket TTS error: {}", payload);
                completed.set(true);
                audioSink.tryEmitComplete();
            } else if (response.containsKey("status") && "finished".equals(response.get("status"))) {
                completed.set(true);
                audioSink.tryEmitComplete();
            }
        }

        @Override
        protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
            // 处理二进制消息（音频数据）
            ByteBuffer buffer = message.getPayload();
            byte[] audioData = new byte[buffer.remaining()];
            buffer.get(audioData);

            // 发送音频数据到接收器
            audioSink.tryEmitNext(audioData);
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            log.error("WebSocket transport error", exception);
            completed.set(true);
            audioSink.tryEmitComplete();
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
            log.debug("WebSocket connection closed: {}", status);
            completed.set(true);
            audioSink.tryEmitComplete();
        }
    }

    /**
     * 处理剩余的文本
     *
     * @param remainingText 剩余文本
     * @param player 音频播放器
     */
    private void processRemainingText(String remainingText, AudioPlayer player) {
        if (remainingText != null && !remainingText.trim().isEmpty()) {
            byte[] audioData = textToSpeech(remainingText);
            if (audioData.length > 0) {
                player.addAudioData(audioData);
            }
        }
    }

    /**
     * 判断是否应该处理缓冲区中的文本
     *
     * @param text 文本
     * @param charCount 字符数
     * @return 是否应该处理
     */
    private boolean shouldProcessBuffer(String text, int charCount) {
        // 如果文本长度超过最大长度的一半，或者包含句子结束符，则处理
        if (charCount > MAX_TEXT_LENGTH / 2) {
            return true;
        }

        Matcher matcher = SENTENCE_PATTERN.matcher(text);
        return matcher.find();
    }

    /**
     * 构建请求体
     *
     * @param text 文本
     * @return 请求体
     */
    private String buildRequestBody(String text) {
        // 豆包语音合成大模型请求体格式
        Map<String, Object> request = new HashMap<>();
        request.put("text", text);
        request.put("model", model);
        request.put("voice", voice);

        Map<String, Object> audioConfig = new HashMap<>();
        audioConfig.put("format", "mp3");
        audioConfig.put("sample_rate", 24000);
        request.put("audio_config", audioConfig);

        try {
            return objectMapper.writeValueAsString(request);
        } catch (IOException e) {
            log.error("Error serializing request body", e);
            return String.format(
                "{\"text\":\"%s\",\"model\":\"%s\",\"voice\":\"%s\",\"audio_config\":{\"format\":\"mp3\",\"sample_rate\":24000}}",
                text, model, voice
            );
        }
    }

    /**
     * 计算签名
     *
     * @param stringToSign 待签名字符串
     * @param secretKey 密钥
     * @return 签名
     */
    private String calculateSignature(String stringToSign, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secretKeySpec);
        byte[] hash = hmacSha256.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * 将长文本分割成多个短文本
     *
     * @param text 长文本
     * @return 短文本列表
     */
    private Iterable<String> splitText(String text) {
        return () -> new Iterator<String>() {
            private int currentPos = 0;

            @Override
            public boolean hasNext() {
                return currentPos < text.length();
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                int endPos = findSplitPosition(text, currentPos);
                String segment = text.substring(currentPos, endPos);
                currentPos = endPos;
                return segment;
            }
        };
    }

    /**
     * 查找分割位置
     *
     * @param text 文本
     * @param startPos 起始位置
     * @return 分割位置
     */
    private int findSplitPosition(String text, int startPos) {
        int endPos = Math.min(startPos + MAX_TEXT_LENGTH, text.length());

        // 如果没有超出文本长度，直接返回
        if (endPos == text.length()) {
            return endPos;
        }

        // 尝试在句子结束符处分割
        int sentenceEndPos = startPos;
        Matcher matcher = SENTENCE_PATTERN.matcher(text.substring(startPos, endPos));
        while (matcher.find()) {
            sentenceEndPos = startPos + matcher.end();
        }

        // 如果找到了句子结束符，在句子结束符处分割
        if (sentenceEndPos > startPos) {
            return sentenceEndPos;
        }

        // 如果没有找到句子结束符，尝试在空格处分割
        int lastSpacePos = text.lastIndexOf(' ', endPos);
        if (lastSpacePos > startPos) {
            return lastSpacePos + 1;
        }

        // 如果没有找到空格，直接在最大长度处分割
        return endPos;
    }

    /**
     * 获取或创建音频播放器
     *
     * @param playerId 播放器ID
     * @return 音频播放器
     */
    private AudioPlayer getOrCreatePlayer(String playerId) {
        return playerMap.computeIfAbsent(playerId, id -> new AudioPlayer());
    }
}

