package com.mdl.tools.validation.cglib;

import com.alibaba.fastjson.JSON;

import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:34 PM
 */
class ExpressionValidateImpl implements IExpressionValidate{

    @Override
    public ValidateUtil build() {
        return new ValidateUtil();
    }

    public IExpressionValidate check(String exprName, Supplier<Boolean> expression){
        Boolean success = expression.get();
        if(!success){
            throw new ValidateException("expression '%s' is not legal", exprName);
        }
        return this;
    }

    public IExpressionValidate equals(String exprName, Supplier exprFirst, Supplier exprSecond){
        Object resultFirst = exprFirst.get();
        Object resultSecond = exprSecond.get();
        if(!JSON.toJSONString(resultFirst).equals(JSON.toJSONString(resultSecond))){
            throw new ValidateException("expression '%s' is not equals", exprName);
        }
        return this;
    }
}
