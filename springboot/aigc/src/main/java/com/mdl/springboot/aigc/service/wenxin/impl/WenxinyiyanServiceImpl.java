package com.mdl.springboot.aigc.service.wenxin.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.enums.ErrorEnum;
import com.mdl.common.utils.HttpUtil;
import com.mdl.common.utils.StringUtil;
import com.mdl.springboot.aigc.domain.wenxin.WenXinChatRequestDTO;
import com.mdl.springboot.aigc.service.openai.ChatGptStreamedHandler;
import com.mdl.springboot.aigc.service.wenxin.IAccessTokenService;
import com.mdl.springboot.aigc.service.wenxin.IWenxinyiyanService;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.proxy.ProxyServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  文心一言服务实现
 *
 *  {@link <a href="https://cloud.baidu.com/doc/WENXINWORKSHOP/s/4lilb2lpf"/a>}
 * @author meidanlong
 * @date 2023/6/26
 * @version 1.0.0
 */
@Slf4j
@Service
public class WenxinyiyanServiceImpl implements IWenxinyiyanService {

    private final static String apiUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=%s";
    private final static String RESULT = "result";

    @Resource
    private IAccessTokenService accessTokenService;

    /**
     * 聊天多轮流式
     * @param requestDTO
     */
    public void chatStream(WenXinChatRequestDTO requestDTO){
        try {
            String url = String.format(apiUrl, accessTokenService.getAccessToken());
            AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
            asyncHttpClient.preparePost(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "text/event-stream")
                    .setBody(JSON.toJSONString(requestDTO))
                    .execute(new ChatGptStreamedHandler())
                    .get();
            asyncHttpClient.close();
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.XXX, e);
        }
    }

    /**
     * 聊天
     * @param requestDTO
     * @return
     */
    public String chat(WenXinChatRequestDTO requestDTO){
        String url = String.format(apiUrl, accessTokenService.getAccessToken());
        try {
            String response = HttpUtil.doPostJson(url, HttpUtil.commonHeaders(), JSON.toJSONString(requestDTO));
            if(StringUtil.isEmpty(response)){
                throw new BusinessException("调用文心一言聊天接口失败");
            }
            return JSON.parseObject(response).getString(RESULT);
        } catch (Exception e) {
            throw new BusinessException(ErrorEnum.XXX, e);
        }
    }

}
