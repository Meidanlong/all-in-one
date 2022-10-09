package com.mdl.demo.project.validation.cglib.base;


/**
 * @description:
 * @author: meidanlong
 * @date: 2021/7/11 8:20 PM
 */
public class BaseException extends RuntimeException{

    private Integer code;

    private String message;

    public BaseException() {
    }

    public BaseException(Integer code, String message) {
        super("code:" + code + " message:" + message);
        this.code = code;
        this.message = message;
    }

    public BaseException(ErrorEnum errorEnum) {
        super("code:" + errorEnum.getCode() + " message:" + errorEnum.getDesc());
        this.code = errorEnum.getCode();
        this.message = errorEnum.getDesc();
    }

    public BaseException(BaseError baseError) {
        super("code:" + baseError.getCode() + " message:" + baseError.getMessage());
        this.code = baseError.getCode();
        this.message = baseError.getMessage();
    }

    public String getMessage(){
        return message;
    }

}
