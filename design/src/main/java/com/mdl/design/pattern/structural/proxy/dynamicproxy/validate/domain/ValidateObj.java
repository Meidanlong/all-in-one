package com.mdl.design.pattern.structural.proxy.dynamicproxy.validate.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:校验对象
 * @author: meidanlong
 * @date: 2022/4/17 7:46 PM
 */
public class ValidateObj {

    private String objName;

    private Object curObj;

    private Map<String, Object> fieldMap = new ConcurrentHashMap<>();

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Object getCurObj() {
        return curObj;
    }

    public void setCurObj(Object curObj) {
        this.curObj = curObj;
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }
}
