package com.mdl.common.domain;

import com.mdl.common.enums.ErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 基础错误
 * @author: meidanlong
 * @date: 2021/3/21 8:16 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseError extends BaseObject {

    private String code;

    private String message;

    public BaseError(String code, String message){
        this.code = code;
        this.message = message;
    }

    public BaseError(ErrorEnum errorEnum){
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }

    public BaseError(BusinessException ex){
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }

}
