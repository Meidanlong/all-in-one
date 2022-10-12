package com.mdl.springboot.demo.project.validation.cglib.base;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/11/7 1:49 PM
 */
public enum ErrorEnum {
    
    PARAM_ERROR(10001, "参数异常"),
    ;

    private Integer code;
    private String desc;

    ErrorEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
