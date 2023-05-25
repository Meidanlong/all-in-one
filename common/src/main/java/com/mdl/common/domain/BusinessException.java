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
    // 配合Log注解使用，记录实际异常
    @Getter
    private Exception exception;

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

    public BusinessException(String message, Exception exception) {
        super(message);
        this.code = ErrorEnum.XXX.getCode();
        this.exception = exception;
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

    public BusinessException(ErrorEnum error, String message, Exception exception) {
        super(message);
        this.code = ErrorEnum.XXX.getCode();
        this.exception = exception;
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
