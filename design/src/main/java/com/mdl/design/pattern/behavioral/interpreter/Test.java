package com.mdl.design.pattern.behavioral.interpreter;

/**
 * Created by geely.
 */
public class Test {
    public static void main(String[] args) {
        String geelyInputStr="6 100 11 + *";
        GeelyExpressionParser expressionParser=new GeelyExpressionParser();
        int result=expressionParser.parse(geelyInputStr);
        System.out.println("解释器计算结果: "+result);
    }
}
