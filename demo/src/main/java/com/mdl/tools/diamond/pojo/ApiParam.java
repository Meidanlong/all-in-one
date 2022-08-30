package com.mdl.tools.diamond.pojo;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 20:39
 */
public class ApiParam {

    private String name;

    private String desc;

    private String generateRule;

    private String defaultValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGenerateRule() {
        return generateRule;
    }

    public void setGenerateRule(String generateRule) {
        this.generateRule = generateRule;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
