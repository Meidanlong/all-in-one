package com.mdl.springboot.aigc.domain.openai;

import com.mdl.common.domain.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author： meidanlong
 * @Created： 2023/2/14 17:10
 */
@Data
public class ChatCompletionResponseDTO extends BaseObject {

    /**
     * 包含请求的唯一标识符的字符串。您可以使用此标识符检索请求的状态和结果
     */
    private String id;

    /**
     * 包含响应的类型。通常，该值为"completion"。
     */
    private String object;

    /**
     * 包含请求创建时间的UTC时间戳。
     */
    private Long created;

    /**
     * 包含使用的OpenAI模型和引擎的名称。
     */
    private String model;

    /**
     * 包含文本自动补全生成的选择的数组。每个选择都是一个对象，其中包含生成的文本，以及与生成的文本相关联的分数和标记。
     */
    private List<ChatCompletionChoice> choices;

    private CompletionUsage usage;

    private CompletionError error;

}
