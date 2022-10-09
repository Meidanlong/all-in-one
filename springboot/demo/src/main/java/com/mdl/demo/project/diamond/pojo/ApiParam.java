package com.mdl.demo.project.diamond.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 20:39
 */
public class ApiParam {

    private String paramName;

    private String paramDesc;

    private Integer demandId;

    private String generateRule;

    private String defaultValue;

    private List<ApiParam> fields = new ArrayList<>();

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
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

    public List<ApiParam> getFields() {
        return fields;
    }

    public void setFields(List<ApiParam> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "{" +
                "\"paramName\": \"" + paramName + '\"' +
                ", \"paramDesc\": \"" + paramDesc + '\"' +
                ", \"demandId\": " + demandId +
                ", \"generateRule\": \"" + generateRule + '\"' +
                ", \"defaultValue\": \"" + defaultValue + '\"' +
                ", \"fields\": " + fields +
                "}";
    }

    private String toJsonString(String str){
        return str.replaceAll(this.getClass().getSimpleName(), "")
                .replaceAll("'", "\"")
                .replaceAll("\\{", "{\"")
                .replaceAll(", ", ", \"")
                .replaceAll("=", "\": ")
                .replaceAll("=\"", "\": \"");
    }
}
