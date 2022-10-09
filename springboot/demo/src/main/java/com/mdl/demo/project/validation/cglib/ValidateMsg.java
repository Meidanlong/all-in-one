package com.mdl.demo.project.validation.cglib;


import com.mdl.demo.project.validation.cglib.base.BaseError;
import com.mdl.demo.project.validation.cglib.base.ErrorEnum;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 6:06 PM
 */
class ValidateMsg {

    private Boolean valid = true;

    private BaseError error;

    public void populateError(String errorMsg, String... params){
        this.valid = false;
        error = new BaseError();
        error.setCode(ErrorEnum.PARAM_ERROR.getCode());
        error.setMessage(String.format(errorMsg, params));
    }

    public Boolean isValid() {
        return valid;
    }

    /*public Boolean isNotValid() {
        return !valid;
    }*/

    public BaseError getError() {
        return error;
    }

    public void setError(BaseError error) {
        this.error = error;
    }

    public void setError(Integer code, String errorMsg){
        this.error = new BaseError();
        this.error.setCode(code);
        this.error.setMessage(errorMsg);
    }
}
