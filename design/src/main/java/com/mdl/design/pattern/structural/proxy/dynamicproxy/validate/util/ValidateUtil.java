package com.mdl.design.pattern.structural.proxy.dynamicproxy.validate.util;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 7:53 PM
 */
public class ValidateUtil {

    private Boolean valid = true;

    private String msg;

    private Object curObj;

//    public Object init(String name, Object obj){
//        ValidateCache.initCache(name, obj);
//        return new ValidateProxy(this).newProxyInstance();
//    }

    public ValidateUtil notNull(String fieldName) throws NoSuchFieldException, IllegalAccessException {
//        Object fieldValue = ValidateHelper.getFieldObj(curObj, fieldName);
//        if(fieldValue == null){
////            valid = false;
//        }
        return this;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getCurObj() {
        return curObj;
    }

    public void setCurObj(Object curObj) {
        this.curObj = curObj;
    }
}
