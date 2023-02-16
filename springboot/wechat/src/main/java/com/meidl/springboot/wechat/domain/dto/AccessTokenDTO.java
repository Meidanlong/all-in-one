package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/16 16:39
 */
@Data
public class AccessTokenDTO extends BaseObject {

    private String accessToken;

    private Long expireTime;
}
