package com.meidl.springboot.wechat.domain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/21 16:38
 */
public enum UserStatusEnum {

    ACTIVATED(1, "已激活"),
    DISABLED(2, "已禁用"),
    UNACTIVATED(3, "未激活"),
    EXITED(4, "推出企业"),
            ;

    private Integer code;
    private String desc;

    UserStatusEnum(int code, String desc) {
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

    public static final Map<Integer, UserStatusEnum> CODE_MAP = new HashMap<>();
    static {
        for (UserStatusEnum e : UserStatusEnum.values()) {
            CODE_MAP.put(e.code, e);
        }
    }

    public static UserStatusEnum getEnum(Integer code){
        return CODE_MAP.get(code);
    }
}
