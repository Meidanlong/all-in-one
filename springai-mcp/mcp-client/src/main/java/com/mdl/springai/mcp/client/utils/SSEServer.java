package com.mdl.springai.mcp.client.utils;

import com.mdl.springai.mcp.client.domain.enums.SSEMsgTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * SSE服务
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
@Slf4j
public class SSEServer {

    // 存放所有用户
    private static final Map<String, SseEmitter> SSE_CLIENTS = new ConcurrentHashMap<>();

    /**
     * @param userId
     * @return SseEmitter
     * @Description: 连接SSE服务
     * @Author meidanlong
     */
    public static SseEmitter connect(String userId) {

        // 设置超时时间，0L表示不超时（永不过期）；默认是30秒，超时未完成任务则会抛出异常
        SseEmitter sseEmitter = new SseEmitter(0L);

        // 注册回调方法
        sseEmitter.onTimeout(timeoutCallback(userId));
        sseEmitter.onCompletion(completionCallback(userId));
        sseEmitter.onError(errorCallback(userId));

        SSE_CLIENTS.put(userId, sseEmitter);

        log.info("SSE连接创建成功，连接的用户ID为：{}", userId);

        return sseEmitter;
    }

    public static void sendMsg(String userId, String message, SSEMsgTypeEnum msgType) {

        if (CollectionUtils.isEmpty(SSE_CLIENTS)) {
            return;
        }

        if (SSE_CLIENTS.containsKey(userId)) {
            SseEmitter sseEmitter = SSE_CLIENTS.get(userId);
            sendEmitterMessage(sseEmitter, userId, message, msgType);
        }

    }

    public static void sendMsgToAllUsers(String message) {

        if (CollectionUtils.isEmpty(SSE_CLIENTS)) {
            return;
        }

        SSE_CLIENTS.forEach((userId, sseEmitter) -> {
                    sendEmitterMessage(sseEmitter, userId, message, SSEMsgTypeEnum.MESSAGE);
                }
        );
    }

    private static void sendEmitterMessage(SseEmitter sseEmitter,
                                           String userId,
                                           String message,
                                           SSEMsgTypeEnum msgType) {

        try {
            SseEmitter.SseEventBuilder msgEvent = SseEmitter.event()
                    .id(userId)
                    .data(message)
                    .name(msgType.type);
            sseEmitter.send(msgEvent);
        } catch (IOException e) {
            log.error("SSE异常...{}", e.getMessage());
            remove(userId);
        }

    }

    public static Consumer<Throwable> errorCallback(String userId) {
        return Throwable -> {
            log.error("SSE异常...");
            // 移除用户连接
            remove(userId);
        };
    }

    public static Runnable timeoutCallback(String userId) {
        return () -> {
            log.info("SSE超时...");
            // 移除用户连接
            remove(userId);
        };
    }

    public static Runnable completionCallback(String userId) {
        return () -> {
            log.info("SSE完成...");
            // 移除用户连接
            remove(userId);
        };
    }

    public static void remove(String userId) {
        // 删除用户
        SSE_CLIENTS.remove(userId);
        log.info("SSE连接被移除，移除的用户ID为：{}", userId);
    }


}
