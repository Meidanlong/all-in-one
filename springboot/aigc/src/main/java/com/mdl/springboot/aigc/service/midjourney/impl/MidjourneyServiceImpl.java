package com.mdl.springboot.aigc.service.midjourney.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.utils.HttpUtil;
import com.mdl.springboot.aigc.constant.MidjourneyConstants;
import com.mdl.springboot.aigc.domain.midjourney.DataModel;
import com.mdl.springboot.aigc.domain.midjourney.InteractionsRequest;
import com.mdl.springboot.aigc.domain.midjourney.OptionModel;
import com.mdl.springboot.aigc.domain.midjourney.SingleImageDataModel;
import com.mdl.springboot.aigc.domain.midjourney.SingleImageRequest;
import com.mdl.springboot.aigc.service.midjourney.IMidjourneyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author meidanlong
 * @date 2023-05-12T16:58:26
 **/
@Slf4j
@Service
public class MidjourneyServiceImpl implements IMidjourneyService {

    @Override
    public String getGridImage(String promptText) {
        Map<String, String> headerMap = Maps.newHashMap();
        // 可配置多账号轮询
        headerMap.put("authorization", MidjourneyConstants.AUTHORIZATION);
        headerMap.put("Content-Type", "application/json");
        InteractionsRequest req = buildInteractionsRequest(promptText);
        String reqJson = JSON.toJSONString(req);
        log.info("===[IMidjourneyService#generateImage] reqJson is {}", reqJson);
        try {
            String res = HttpUtil.doProxyPostJson(MidjourneyConstants.API_URL, headerMap, reqJson);
            if(!StringUtils.isEmpty(res)){
                // 正常请求返回应为空字符串。如果遇到限流等异常会有错误信息
                log.error("!==[IMidjourneyService#generateImage] error, result={}, request={}", res, reqJson);
                throw new BusinessException("调用MJ生图失败，结果返回异常");
            }
            return MidjourneyConstants.SUCCESS;
        } catch (Exception e) {
            return MidjourneyConstants.FAILED;
        }
    }

    @Override
    public String getUpscalerImage(Long messageId, String customId) {
        Map<String, String> headerMap = Maps.newHashMap();
        headerMap.put("authorization", MidjourneyConstants.AUTHORIZATION);
        headerMap.put("Content-Type", "application/json");
        SingleImageRequest req = buildSingleImageRequest(messageId, customId);
        String reqJson = JSON.toJSONString(req);
        log.info("===[IMidjourneyService#getSingleImage] reqJson is {}", reqJson);
        try {
            String res = HttpUtil.doProxyPostJson(MidjourneyConstants.API_URL, headerMap, reqJson);
            if(!StringUtils.isEmpty(res)){
                log.error("!==[IMidjourneyService#getSingleImage] error, result={}, request={}", res, reqJson);
                throw new BusinessException("调用MJ单图失败，结果返回异常");
            }
            return MidjourneyConstants.SUCCESS;
        } catch (Exception e) {
            return MidjourneyConstants.FAILED;
        }
    }

    private InteractionsRequest buildInteractionsRequest(String promptText) {
        InteractionsRequest request = new InteractionsRequest();
        request.setType(2);
        request.setApplicationId(MidjourneyConstants.APPLICATION_ID);
        request.setGuildId(MidjourneyConstants.GUILD_ID);
        request.setChannelId(MidjourneyConstants.CHANNEL_ID);
        request.setSessionId(MidjourneyConstants.SESSION_ID);
        DataModel data = new DataModel();
        data.setVersion(MidjourneyConstants.DATA_VERSION);
        data.setId(MidjourneyConstants.DATA_ID);
        data.setName("imagine");
        data.setType(1);
        OptionModel option = new OptionModel();
        option.setType(3);
        option.setName("prompt");
        option.setValue(promptText);

        data.setOptions(Lists.newArrayList(option));
        request.setData(data);
        return request;

    }

    private SingleImageRequest buildSingleImageRequest(Long messageId, String customId){
        SingleImageRequest request = new SingleImageRequest();
        request.setType(3);
        request.setApplicationId(MidjourneyConstants.APPLICATION_ID);
        request.setGuildId(MidjourneyConstants.GUILD_ID);
        request.setChannelId(MidjourneyConstants.CHANNEL_ID);
        request.setSessionId(MidjourneyConstants.SESSION_ID);
        request.setMessageId(messageId);
        SingleImageDataModel data = new SingleImageDataModel();
        data.setCustomId(customId);
        request.setData(data);
        return request;
    }

}
