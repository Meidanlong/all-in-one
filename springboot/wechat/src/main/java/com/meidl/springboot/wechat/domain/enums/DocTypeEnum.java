package com.meidl.springboot.wechat.domain.enums;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/16 19:00
 */
public enum DocTypeEnum {

    DOC(3, "文档"),
    Table(4, "表格")
    ;

    private Integer code;
    private String desc;

    DocTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
