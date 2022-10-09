package com.mdl.demo.project.validation.cglib;

import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:34 PM
 */
public interface IExpressionValidate {

    /**
     * 对象构建方法，需保留
     * @return
     */
    ValidateUtil build();
    /**
     * 检验表达式是否合法
     * @param exprName
     * @param expression
     * @return
     */
    IExpressionValidate check(String exprName, Supplier<Boolean> expression);

    /**
     * 检验两个表达式结果是否相等
     * @param exprName
     * @param exprFirst
     * @param exprSecond
     * @return
     */
    IExpressionValidate equals(String exprName, Supplier exprFirst, Supplier exprSecond);
}
