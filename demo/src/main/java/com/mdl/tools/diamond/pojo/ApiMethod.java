package com.mdl.tools.diamond.pojo;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 20:36
 */
public class ApiMethod {

    private String appName;

    private String beanName;

    private List<ApiParam> params;


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

    public List<ApiParam> getParams() {
        return params;
    }

    public void setParams(List<ApiParam> params) {
        this.params = params;
    }
}
