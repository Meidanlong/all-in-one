package com.meidl.springboot.wechat.service.workweixin.crop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.utils.HttpUtil;
import com.meidl.springboot.wechat.domain.dto.ChatMessageDTO;
import com.meidl.springboot.wechat.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:群聊消息服务
 * @author: meidanlong
 * @date: 2023/3/7 09:55
 */
@Service
public class ChatMessageService {

    private final static String CHAT_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/appchat/send?access_token=%s";

    @Resource
    private AccessTokenService accessTokenService;

    /**
     * 发送群聊消息
     *
     *  {@link <a href="https://developer.work.weixin.qq.com/document/path/90248"/a>}
     * @author meidanlong
     * @date 2023/3/7
     * @version 1.0.0
     * @param chatMessageDTO fieldDesc @required
     * @return void
     */
    public void sendChatMessage(ChatMessageDTO chatMessageDTO){
        String token = accessTokenService.getAccessToken();
        String url = String.format(CHAT_MESSAGE_URL, token);
        String result = HttpUtil.doPostJson(url, null, JSON.toJSONString(chatMessageDTO));
        JSONObject jsonObject = ResultUtil.getResult(result, "企业微信发送群聊消息");
    }
}
