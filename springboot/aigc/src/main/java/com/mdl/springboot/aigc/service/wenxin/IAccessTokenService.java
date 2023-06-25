package com.mdl.springboot.aigc.service.wenxin;

public interface IAccessTokenService {


    /**
     * 获取accesstoken
     *
     *  {@link <a href="https://ai.baidu.com/docs#/Auth/top"/a>}
     * @author meidanlong
     * @date 2023/6/25
     * @version 1.0.0
     * @return String
     */
    String getAccessToken();
}
