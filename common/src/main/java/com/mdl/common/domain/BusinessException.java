package com.mdl.common.domain;

import com.mdl.common.enums.ErrorEnum;
import lombok.Getter;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/9/5 11:46
 */
public class BusinessException extends RuntimeException {

    /**
     * 异常编号
     */
    @Getter
    private String code;

    /**
     * 根据枚举构造业务类异常
     * @param error
     */
    public BusinessException(ErrorEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

    /**
     * 自定义消息体构造业务类异常
     * @param message
     */
    public BusinessException(String message) {
        super(message);
        this.code = ErrorEnum.XXX.getCode();
    }

    /**
     * 自定义消息体构造业务类异常
     * @param error
     * @param message
     */
    public BusinessException(ErrorEnum error, String message) {
        super(message);
        this.code = error.getCode();
    }

    /**
     * 根据异常构造业务类异常
     * @param error
     * @param cause
     */
    public BusinessException(ErrorEnum error, Throwable cause) {
        super(cause);
        this.code = error.getCode();
    }
}
