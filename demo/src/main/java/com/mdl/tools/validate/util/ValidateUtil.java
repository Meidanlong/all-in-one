package com.mdl.tools.validate.util;

/**
 * @description:校验工具类
 * 目标：打造一个对象参数校验工具
 * 特点：
 * 1、滞后获取被校验对象
 * 2、简洁链式调用
 * 3、调用层级清晰
 * 4、校验复杂对象
 * 5、校验异常短路后续校验
 * 6、包装返回结果
 *
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
