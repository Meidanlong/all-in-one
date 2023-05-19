package com.mdl.springboot.aigc.domain.midjourney;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交互请求
 *
 * @author meidanlong
 * @date 2023-05-15T10:57:09
 **/
@NoArgsConstructor
@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class SingleImageRequest {

    /**
     * 类型
     */
    private Integer type = 3;
    /**
     * 应用程序id
     */
    private String applicationId;
    /**
     * 公会id(服务器id)
     */
    private String guildId;
    /**
     * 通道标识
     */
    private String channelId;
    /**
     * 会话id
     */
    private String sessionId;
    /**
     * 现时标志
     */
    private Object nonce;
    /**
     * 消息id
     */
    private Long messageId;
    /**
     *
     */
    private Integer messageFlag = 0;
    /**
     * 数据
     */
    private SingleImageDataModel data;
}
