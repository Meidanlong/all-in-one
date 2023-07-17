package com.mdl.springboot.aigc.domain.wenxin;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.mdl.common.domain.BaseObject;
import lombok.Data;

@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class ImageDetailRespDTO extends BaseObject {

    private Integer width;

    private Integer height;

    private String imgUrl;

    private String imgApproveConclusion;
}
