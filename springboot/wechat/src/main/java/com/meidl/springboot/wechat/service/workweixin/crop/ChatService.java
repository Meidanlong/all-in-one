package com.meidl.springboot.wechat.service.workweixin.crop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.utils.HttpUtil;
import com.meidl.springboot.wechat.domain.dto.ChatDTO;
import com.meidl.springboot.wechat.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:群聊服务
 * @author: meidanlong
 * @date: 2023/3/7 09:30
 */
@Slf4j
@Service
public class ChatService {

    @Resource
    private AccessTokenService accessTokenService;

    // 创建群聊链接
    private final String CREATE_CHAT_URL = "https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token=%s";

    /**
     * 创建公司内部群聊（需是公司内部人员）
     *
     *  {@link <a href="https://developer.work.weixin.qq.com/document/path/90245"/a>}
     * @author meidanlong
     * @date 2023/3/7
     * @version 1.0.0
     * @param chatDTO fieldDesc @required
     * @return String
     */
    public String createChat(ChatDTO chatDTO){
        String token = accessTokenService.getAccessToken();
        String url = String.format(CREATE_CHAT_URL, token);
        String result = HttpUtil.doPostJson(url, null, JSON.toJSONString(chatDTO));
        JSONObject jsonObject = ResultUtil.getResult(result, "企业微信根据手机号获取userid");
        return jsonObject.getString("chatid");
    }

}
