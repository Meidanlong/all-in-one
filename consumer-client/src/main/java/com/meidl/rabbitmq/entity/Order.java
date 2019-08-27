package com.meidl.rabbitmq.entity;

import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = 3344413427140567739L;
    private String id;

    private String content;

    //存储消息发送的唯一标识
    private String messageId;

    public Order() {
    }

    public Order(String id, String content, String messageId) {
        this.id = id;
        this.content = content;
        this.messageId = messageId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}