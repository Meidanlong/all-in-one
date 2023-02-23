package com.meidl.springboot.wechat.service.workweixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.enums.ErrorEnum;
import com.mdl.common.utils.HttpUtil;
import com.mdl.common.utils.StringUtil;
import com.meidl.springboot.wechat.domain.dto.AccessTokenDTO;
import com.meidl.springboot.wechat.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/16 16:37
 */
@Slf4j
@Service
public class AccessTokenService {

    // 本地缓存
    private AccessTokenDTO accessTokenDTO;

    private final static String corpid = "wwe3014706067ed8c4";
    // 小黑屋-推送小助手
    private final static String corpsecret = "IhSB8FbfIWf4BoizpgjjGMGnTJUPhbURVusX0HN39rg";
    private final static String qwUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    // 安全毫秒数
    private final static Long SAFE_MS = 100L;
    private final static Long RETRY_MS = 10L;

    /**
     * 获取AccessToken
     *
     *  {@link <a href="https://developer.work.weixin.qq.com/document/10013#第三步：获取access-token"/a>}
     * @author meidanlong
     * @date 2023/2/16
     * @version 1.0.0
     * @return AccessTokenDTO
     */
    public String getAccessToken(){
        if(accessTokenDTO != null && accessTokenDTO.getExpireTime().compareTo(System.currentTimeMillis()) > 0){
            log.info("==[AccessTokenService#getAccessToken] already had accessToken");
            return accessTokenDTO.getAccessToken();
        }
        // 获取企业微信accessToken
        Map<String, String> param = new HashMap<>();
        param.put("corpid", corpid);
        param.put("corpsecret", corpsecret);
        String result = HttpUtil.doGet(qwUrl, null, param);
        JSONObject jsonObject = ResultUtil.getResult(result, "获取企业微信accessToken链接");
        accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setAccessToken(jsonObject.getString("access_token"));
        Long expires_in = jsonObject.getLong("expires_in") * 1000L;
        Long expireTime = System.currentTimeMillis() + expires_in - SAFE_MS;
        if(expireTime <= RETRY_MS){
            return getAccessToken();
        }
        accessTokenDTO.setExpireTime(expireTime);
        return accessTokenDTO.getAccessToken();
    }


}
