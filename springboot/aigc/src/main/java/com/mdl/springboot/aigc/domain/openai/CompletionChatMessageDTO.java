package com.mdl.springboot.aigc.domain.openai;

import com.mdl.common.domain.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/3/3 09:57
 */
@Data
public class CompletionChatMessageDTO extends BaseObject {

    private String role;

    private String content;
}
