package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/16 19:12
 */
@Data
public class DocDTO extends BaseObject {

    private String url;

    private String docId;
}
