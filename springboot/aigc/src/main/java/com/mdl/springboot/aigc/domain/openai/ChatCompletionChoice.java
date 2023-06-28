package com.mdl.springboot.aigc.domain.openai;

import com.alibaba.fastjson.annotation.JSONField;
import com.mdl.common.domain.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author： meidanlong
 * @Created： 2023/2/14 19:03
 */
@Data
public class ChatCompletionChoice extends BaseObject {

    /**
     * 包含生成的文本的字符串
     */
    private CompletionChatMessageDTO delta;

    /**
     * 包含生成的文本在选择列表中的索引。
     */
    private Integer index;

    /**
     * 包含文本自动补全停止的原因的字符串。常见的原因包括"length"（已达到最大标记数）和"stop"（已达到停止标记）。
     */
    @JSONField(name = "finish_reason")
    private String finishReason;

}
