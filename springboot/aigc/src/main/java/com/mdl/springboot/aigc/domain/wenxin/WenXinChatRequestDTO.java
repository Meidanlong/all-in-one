package com.mdl.springboot.aigc.domain.wenxin;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.mdl.common.domain.BaseObject;
import com.mdl.springboot.aigc.domain.openai.CompletionChatMessageDTO;
import lombok.Data;

import java.util.List;

/**
 * @description: 文心一言请求体
 * @author meidanlong
 * @date 2023年07月18日
 * @version: 1.0
 */
@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class WenXinChatRequestDTO extends BaseObject {

    /**
     *  聊天上下文信息。说明：
     * （1）messages成员不能为空，1个成员表示单轮对话，多个成员表示多轮对话
     * （2）最后一个message为当前请求的信息，前面的message为历史对话信息
     * （3）必须为奇数个成员，成员中message的role必须依次为user、assistant
     * （4）最后一个message的content长度（即此轮对话的问题）不能超过2000个字符；如果messages中content总长度大于2000字符，系统会依次遗忘最早的历史会话，直到content的总长度不超过2000个字符
     */
    private List<CompletionChatMessageDTO> messages;

    /**
     * 是否以流式接口的形式返回数据，默认false
     */
    private Boolean stream;

    /**
     * 表示最终用户的唯一标识符，可以监视和检测滥用行为，防止接口恶意调用
     */
    private String userId;
}
