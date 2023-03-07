package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/3/7 10:00
 */
@Data
public class ChatMessageTextDTO extends BaseObject {

    private String content;
}
