package com.mdl.springboot.aigc.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mdl.common.utils.HttpUtil;
import com.mdl.springboot.aigc.constant.MidjourneyContants;
import com.mdl.springboot.aigc.domain.midjourney.ApplicationCommandModel;
import com.mdl.springboot.aigc.domain.midjourney.DataModel;
import com.mdl.springboot.aigc.domain.midjourney.InteractionsRequest;
import com.mdl.springboot.aigc.domain.midjourney.OptionModel;
import com.mdl.springboot.aigc.domain.midjourney.SingleImageDataModel;
import com.mdl.springboot.aigc.domain.midjourney.SingleImageRequest;
import com.mdl.springboot.aigc.service.IMidjourneyService;
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
    public String generateImage(String promptText) {
        Map<String, String> headerMap = Maps.newHashMap();
        headerMap.put("authorization", MidjourneyContants.AUTHORIZATION);
        headerMap.put("Content-Type", "application/json");
        InteractionsRequest req = buildInteractionsRequest(promptText);
        String reqJson = JSON.toJSONString(req);
        log.info("===reqJson is {}", reqJson);
        try {
            String res = HttpUtil.doProxyPostJson(MidjourneyContants.API_URL, headerMap, reqJson);
            return res;
        } catch (Exception e) {
            log.error("VolcanoTtsService generateVoice error,request={}", reqJson, e);
        }
        return StringUtils.EMPTY;
    }

    @Override
    public String getSingleImage(Long messageId, String customId) {
        Map<String, String> headerMap = Maps.newHashMap();
        headerMap.put("authorization", MidjourneyContants.AUTHORIZATION);
        headerMap.put("Content-Type", "application/json");
        SingleImageRequest req = buildSingleImageRequest(messageId, customId);
        String reqJson = JSON.toJSONString(req);
        log.info("===reqJson is {}", reqJson);
        try {
            String res = HttpUtil.doProxyPostJson(MidjourneyContants.API_URL, headerMap, reqJson);
            return res;
        } catch (Exception e) {
            log.error("VolcanoTtsService generateVoice error,request={}", reqJson, e);
        }
        return StringUtils.EMPTY;
    }

    private InteractionsRequest buildInteractionsRequest(String promptText) {
        InteractionsRequest request = new InteractionsRequest();
        request.setType(2);
        request.setApplicationId("936929561302675456");
        request.setGuildId("1073445198857773096");
        request.setChannelId("1073445808353071104");
        request.setSessionId("500c14391ba2dba4f310a08a2891463b");
        DataModel data = new DataModel();
        data.setVersion("1077969938624553050");
        data.setId("938956540159881230");
        data.setName("imagine");
        data.setType(1);
        OptionModel option = new OptionModel();
        option.setType(3);
        option.setName("prompt");
        option.setValue(promptText);

        data.setOptions(Lists.newArrayList(option));
        ApplicationCommandModel applicationCommandModel = new ApplicationCommandModel();
        applicationCommandModel.setId("938956540159881230");
        applicationCommandModel.setApplicationId("936929561302675456");
        applicationCommandModel.setVersion("1077969938624553050");
        applicationCommandModel.setType(1);
        applicationCommandModel.setNsfw(false);
        applicationCommandModel.setName("imagine");
        applicationCommandModel.setDescription("Create images with Midjourney");
        applicationCommandModel.setDmPermission(true);
        OptionModel applicationOption = new OptionModel();
        applicationOption.setType(3);
        applicationOption.setName("prompt");
        applicationOption.setDescription("The prompt to imagine");
        applicationOption.setRequired(true);

        applicationCommandModel.setOptions(Lists.newArrayList(applicationOption));

        data.setApplicationCommand(applicationCommandModel);
        data.setAttachments(Lists.newArrayList());
        request.setData(data);
        return request;

    }


    private SingleImageRequest buildSingleImageRequest(Long messageId, String customId){
        SingleImageRequest request = new SingleImageRequest();
        request.setType(3);
        request.setApplicationId("936929561302675456");
        request.setGuildId("1073445198857773096");
        request.setChannelId("1073445808353071104");
        request.setSessionId("500c14391ba2dba4f310a08a2891463b");
        request.setMessageId(messageId);
        SingleImageDataModel data = new SingleImageDataModel();
        data.setCustomId(customId);
        request.setData(data);
        return request;
    }

}
