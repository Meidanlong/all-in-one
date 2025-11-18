package com.mdl.springai.mcp.server.domain;

import lombok.Data;

/**
 * 推送消息请求对象
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
@Data
public class TPushMessageReq {

    /**
     * 业务方标识
     */
    private String businessId;

    /**
     * 推送数据
     */
    private String data;
}

