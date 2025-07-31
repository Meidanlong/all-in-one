package com.mdl.springai.mcp.client.domain.dto;

import com.mdl.common.domain.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName ChatEntity
 * @Author meidanlong
 * @Version 1.0
 * @Description ChatEntity
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatRequestDTO extends BaseObject {

    private String currentUserName;
    private String message;
    private String botMsgId;

}
