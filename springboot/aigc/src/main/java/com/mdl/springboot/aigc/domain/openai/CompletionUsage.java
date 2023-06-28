package com.mdl.springboot.aigc.domain.openai;

import com.alibaba.fastjson.annotation.JSONField;
import com.mdl.common.domain.BaseObject;
import lombok.Data;

/**
 * @Author： Luzelong
 * @Created： 2023/2/14 19:03
 */
@Data
public class CompletionUsage extends BaseObject {

    @JSONField(name = "prompt_tokens")
    private Integer promptTokens;

    @JSONField(name = "completion_tokens")
    private Integer completionTokens;

    @JSONField(name = "total_tokens")
    private Integer totalTokens;

}
