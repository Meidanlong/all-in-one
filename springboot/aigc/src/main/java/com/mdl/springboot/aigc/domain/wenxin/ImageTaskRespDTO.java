package com.mdl.springboot.aigc.domain.wenxin;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.mdl.common.domain.BaseObject;
import lombok.Data;

import java.util.List;

@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class ImageTaskRespDTO extends BaseObject {

    private Long taskId;

    private Integer taskProgress;

    private String taskStatus;

    private List<ImageSubTaskRespDTO> subTaskResultList;
}
