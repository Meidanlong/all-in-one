package com.mdl.springboot.aigc.domain.enums;

/**
 * 文心一言角色枚举
 */
public enum WenxinyiyanRoleEnum {
    USER("user"),
    ASSISTANT("assistant"),
    ;


    private String data;

    WenxinyiyanRoleEnum(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
