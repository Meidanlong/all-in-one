package com.mdl.tools.validation.cglib;


import com.mdl.tools.validation.cglib.base.BaseObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:37 PM
 */
class ValidateObject extends BaseObject {

    private String objName;

    private Object curObj;

    private Map<String, Object> fieldMap = new ConcurrentHashMap<>();

    private Map<String, Object> resetMap = new ConcurrentHashMap<>();

    public ValidateObject(String objName, Object curObj) {
        this.objName = objName;
        this.curObj = curObj;
    }

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

    public void setFieldMapData(String fieldName, Object obj){
        fieldMap.put(fieldName, obj);
    }

    public Object getFieldMapData(String fieldName){
        return fieldMap.get(fieldName);
    }

    public Map<String, Object> getResetMap() {
        return resetMap;
    }

    public void removeResetMap() {
        this.resetMap = new ConcurrentHashMap<>();
    }

    public void populateResetMap(String fieldName, Object value){
        this.fieldMap.put(fieldName, value);
        this.resetMap.put(fieldName, value);
    }
}
