package com.mdl.tools.diamond.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 20:36
 */
public class ApiMethod {

    private String appName;

    private String beanName;

    private String methodName;

    private String methodDesc;

    private Integer demandId;

    private List<ApiParam> params = new ArrayList<>();

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public List<ApiParam> getParams() {
        return params;
    }

    public void setParams(List<ApiParam> params) {
        this.params = params;
    }


    @Override
    public String toString() {
        return "{" +
                "\"appName\": \"" + appName + '\"' +
                ", \"beanName\": \"" + beanName + '\"' +
                ", \"methodName\": \"" + methodName + '\"' +
                ", \"methodDesc\": \"" + methodDesc + '\"' +
                ", \"demandId\": " + demandId +
                ", \"params\":" + params +
                '}';
    }
}
