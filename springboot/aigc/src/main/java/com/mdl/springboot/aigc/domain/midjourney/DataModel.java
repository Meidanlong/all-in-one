package com.mdl.springboot.aigc.domain.midjourney;

/**
 * @author meidanlong
 * @date 2023-05-15T11:14:17
 **/

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据模型
 *
 * @author meidanlong
 * @date 2023/05/15
 */
@NoArgsConstructor
@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class DataModel {

    /**
     * 版本
     */
    private String version;
    /**
     * id
     */
    private String id;
    /**
     * 名字
     */
    private String name;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 选项
     */
    private List<OptionModel> options;
    /**
     * 应用程序命令
     */
    private ApplicationCommandModel applicationCommand;
    /**
     * 附件
     */
    private List<?> attachments;
}
