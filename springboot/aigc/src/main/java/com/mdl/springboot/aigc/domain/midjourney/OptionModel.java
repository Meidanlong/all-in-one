package com.mdl.springboot.aigc.domain.midjourney;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 选择模型
 *
 * @author meidanlong
 * @date 2023/05/15
 */
@NoArgsConstructor
@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class OptionModel {
    /**
     * 类型
     */
    private Integer type;
    /**
     * 名字
     */
    private String name;

    /**
     * 值
     */
    private String value;
    /**
     * 描述
     */
    private String description;
    /**
     * 要求
     */
    private Boolean required;
}
