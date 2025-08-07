package com.mdl.springai.mcp.client.service.impl;

import com.mdl.springai.mcp.client.service.ITtsService;
import com.mdl.springai.mcp.client.utils.AudioPlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义TTS服务实现 - 使用火山引擎豆包语音合成大模型
 *
 * @author meidanlong
 * @date 2025年08月06日
 * @version: 1.0
 */
@Slf4j
@Service
public class CustomTtsServiceImpl implements ITtsService {

    private static final int MAX_TEXT_LENGTH = 500; // 豆包语音合成大模型单次请求最大文本长度
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("[,.!?;，。！？；]");

    @Value("${volcano.tts.access-key}")
    private String accessKey;

    @Value("${volcano.tts.secret-key}")
    private String secretKey;

    @Value("${volcano.tts.region:cn-north-1}")
    private String region;

    @Value("${volcano.tts.host:open.volcengineapi.com}")
    private String host;

    @Value("${volcano.tts.model:douyin-tts-pro}")
    private String model;

    @Value("${volcano.tts.voice:zh_female_qingxin}")
    private String voice;

    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;
    private final ConcurrentHashMap<String, AudioPlayer> playerMap = new ConcurrentHashMap<>();

    @Autowired
    public CustomTtsServiceImpl(RestTemplate restTemplate, RetryTemplate retryTemplate) {
        this.restTemplate = restTemplate;
        this.retryTemplate = retryTemplate;
    }

    @Override
    public byte[] textToSpeech(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new byte[0];
        }

        try {
            // 构建请求体
            String requestBody = buildRequestBody(text);

            // 构建请求头
            HttpHeaders headers = buildHeaders(requestBody);

            // 构建请求实体
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // 发送请求并重试
            ResponseEntity<byte[]> responseEntity = retryTemplate.execute(context -> restTemplate.exchange(
                "https://" + host + "/api/v1/tts/generation",
                HttpMethod.POST,
                requestEntity,
                byte[].class
            ));

            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                log.error("TTS API error: {}", responseEntity.getStatusCode());
                return new byte[0];
            }

            byte[] responseBody = responseEntity.getBody();
            if (responseBody == null) {
                log.error("TTS API returned empty response");
                return new byte[0];
            }

            return responseBody;
        } catch (Exception e) {
            log.error("Error converting text to speech", e);
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
                .flatMap(segment ->
                    Mono.fromCallable(() -> textToSpeech(segment))
                        .subscribeOn(Schedulers.boundedElastic())
                );
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

                    // 异步处理TTS转换和播放
                    Mono.fromCallable(() -> textToSpeech(textToProcess))
                        .subscribeOn(Schedulers.boundedElastic())
                        .subscribe(audioData -> {
                            if (audioData.length > 0) {
                                player.addAudioData(audioData);
                            }
                        });
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
        return String.format(
            "{\"text\":\"%s\",\"model\":\"%s\",\"voice\":\"%s\",\"audio_config\":{\"format\":\"mp3\",\"sample_rate\":24000}}",
            text, model, voice
        );
    }

    /**
     * 构建请求头
     *
     * @param requestBody 请求体
     * @return 请求头
     */
    private HttpHeaders buildHeaders(String requestBody) throws NoSuchAlgorithmException, InvalidKeyException {
        String path = "/api/v1/tts/generation";
        String method = "POST";
        String contentType = "application/json";
        String date = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME);

        // 计算签名
        String stringToSign = method + "\n" + path + "\n" + date + "\n" + contentType + "\n" + requestBody;
        String signature = calculateSignature(stringToSign, secretKey);

        // 构建授权头
        String authorization = "HMAC-SHA256 AccessKey=" + accessKey + ", Signature=" + signature;

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Date", date);
        headers.set("Authorization", authorization);
        headers.set("X-Date", date);
        headers.set("Host", host);

        return headers;
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

