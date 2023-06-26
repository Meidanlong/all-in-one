package com.mdl.springboot.aigc.service.wenxin.impl;

import com.alibaba.fastjson.JSON;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.utils.HttpUtil;
import com.mdl.common.utils.StringUtil;
import com.mdl.springboot.aigc.domain.enums.WenxinyigeImageRatioEnum;
import com.mdl.springboot.aigc.domain.wenxin.*;
import com.mdl.springboot.aigc.service.wenxin.IAccessTokenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文心一格服务
 *
 *  {@link <a href="https://cloud.baidu.com/doc/NLP/s/1lg53dryv"/a>}
 * @author meidanlong
 * @date 2023/6/21
 * @version 1.0.0
 */
@Slf4j
@Service
public class AccessTokenServiceImpl implements IAccessTokenService {

    private final static String ACCESS_TOKEN_URL="https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s";
    private final static String API_KEY = "0bNqbGV8O03h4EdnwtuydxUH";
    private final static String SECRET_KEY = "PdHjA8qOgyuG2smsgFzDMRqK6hRSpCok";

    public String getAccessToken(){
        String url = String.format(ACCESS_TOKEN_URL, API_KEY, SECRET_KEY);
        String response = HttpUtil.doPost(url);
        if(StringUtil.isEmpty(response)){
            throw new BusinessException("文心获取accessToken异常");
        }
        log.info("[IWenxinyigeService#getAccessToken] - response={}", response);
        AccessTokenDTO accessToken = JSON.parseObject(response, AccessTokenDTO.class);
        // todo accessToken缓存处理
        return accessToken.getAccessToken();
    }


    @SneakyThrows
    public static void main(String[] args) {
        AccessTokenServiceImpl service = new AccessTokenServiceImpl();
//         获取accesstoken
        String accessToken = service.getAccessToken();
        System.out.println(accessToken);

    }

}
