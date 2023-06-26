package com.mdl.springboot.aigc.domain.wenxin;

import com.mdl.common.domain.BaseObject;
import com.mdl.springboot.aigc.domain.enums.WenxinyiyanRoleEnum;
import lombok.Data;

/**
 * 文心一言上下文消息对象
 */
@Data
public class TextMessageDTO extends BaseObject {

    /**
     * 当前支持以下：
     * user: 表示用户
     * assistant: 表示对话助手
     */
    private WenxinyiyanRoleEnum role;
    /**
     * 对话内容，不能为空
     */
    private String content;
}
