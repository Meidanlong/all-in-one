package com.mdl.springboot.aigc.domain.midjourney;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 应用程序命令模型
 *
 * @author meidanlong
 * @date 2023/05/15
 */
@NoArgsConstructor
@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class ApplicationCommandModel {
    /**
     * id
     */
    private String id;
    /**
     * 应用程序id
     */
    private String applicationId;
    /**
     * 版本
     */
    private String version;
    /**
     * 默认成员权限
     */
    private Object defaultMemberPermissions;
    /**
     * 类型
     */
    private Integer type;
    /**
     * nsfw (
     */
    private Boolean nsfw;
    /**
     * 名字
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * dm许可
     */
    private Boolean dmPermission;
    /**
     * 上下文
     */
    private Object contexts;
    /**
     * 选项
     */
    private List<OptionModel> options;

}
