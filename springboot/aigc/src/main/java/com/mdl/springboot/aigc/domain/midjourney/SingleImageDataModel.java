package com.mdl.springboot.aigc.domain.midjourney;

/**
 * @author meidanlong
 * @date 2023-05-15T11:14:17
 **/

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据模型
 *
 * @author meidanlong
 * @date 2023/05/15
 */
@NoArgsConstructor
@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class SingleImageDataModel {

    /**
     * 类型
     */
    private Integer componentType = 2;
    /**
     * 自定义id
     */
    private String customId;
}
