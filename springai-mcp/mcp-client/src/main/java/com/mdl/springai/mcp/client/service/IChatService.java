package com.mdl.springai.mcp.client.service;

/**
 * 聊天服务
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
public interface IChatService {

    /**
     * 聊天
     *
     * @param prompt
     * @return
     */
    String chat(String prompt);
}
