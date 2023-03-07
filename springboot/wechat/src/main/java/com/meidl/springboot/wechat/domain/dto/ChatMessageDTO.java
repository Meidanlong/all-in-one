package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/3/7 09:56
 */
@Data
public class ChatMessageDTO extends BaseObject {

    /**
     * 群聊id
     */
    private String chatid;
    /**
     * 消息类型
     */
    private String msgtype;
    /**
     * 消息内容，最长不超过2048个字节
     */
    private ChatMessageTextDTO text;
    /**
     * 表示是否是保密消息，0表示否，1表示是，默认0
     */
    private Integer safe = 0;

    public void setTextContent(String content) {
        ChatMessageTextDTO chatMessageTextDTO = new ChatMessageTextDTO();
        chatMessageTextDTO.setContent(content);
        this.text = chatMessageTextDTO;
    }
}
