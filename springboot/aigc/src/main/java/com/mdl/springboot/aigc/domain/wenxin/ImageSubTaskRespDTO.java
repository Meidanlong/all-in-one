package com.mdl.springboot.aigc.domain.wenxin;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.mdl.common.domain.BaseObject;
import lombok.Data;

import java.util.List;

@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class ImageSubTaskRespDTO extends BaseObject {

    private Integer subTaskErrorCode;

    private Integer subTaskProgress;

    private String subTaskStatus;

    private List<ImageDetailRespDTO> finalImageList;
}
