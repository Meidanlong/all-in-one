package com.mdl.springboot.aigc.service;

import java.util.List;
import java.util.Map;

/**
 * Midjourney服务
 *
 * @author meidanlong
 * @date 2023-05-12T16:58:09
 **/
public interface IMidjourneyService {
    /**
     * 生成图像
     *
     * @param promptText 提示文本
     * @return {@link String}
     */
    String generateImage(String promptText);

    /**
     * 获取单个图片
     * @param messageId
     * @param customId
     * @return
     */
    String getSingleImage(Long messageId, String customId);

}
