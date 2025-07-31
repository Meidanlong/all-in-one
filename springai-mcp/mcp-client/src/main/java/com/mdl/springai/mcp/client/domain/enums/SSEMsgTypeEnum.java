package com.mdl.springai.mcp.client.domain.enums;

/**
 * SSE消息类型
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
public enum SSEMsgTypeEnum {

    MESSAGE("message", "单词发送的普通类型消息"),
    ADD("add", "消息追加，适用于流式stream推送"),
    FINISH("finish", "消息完成"),
    CUSTOM_EVENT("custom_event", "单词发送的普通类型消息"),
    DONE("done", "单词发送的普通类型消息");    // ChatGLM v4

    public final String type;
    public final String value;

    SSEMsgTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
