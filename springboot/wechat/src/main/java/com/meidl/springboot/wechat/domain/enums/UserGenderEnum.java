package com.meidl.springboot.wechat.domain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/21 16:38
 */
public enum UserGenderEnum {

    UNDEFINED(0, "未定义"),
    MALE(1, "男性"),
    FEMALE(2, "女性"),
            ;

    private Integer code;
    private String desc;

    UserGenderEnum(int code, String desc) {
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


    public static final Map<Integer, UserGenderEnum> CODE_MAP = new HashMap<>();
    static {
        for (UserGenderEnum e : UserGenderEnum.values()) {
            CODE_MAP.put(e.code, e);
        }
    }

    public static UserGenderEnum getEnum(Integer code){
        return CODE_MAP.get(code);
    }

}
