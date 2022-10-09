package com.mdl.demo.project.validation.jdk;

import java.util.List;
import java.util.Stack;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 7:47 PM
 */
public class ValidateStack {

    private Stack<List<ValidateObj>> validateObjs;


    public void push(List<ValidateObj> validateObjList){
        if(validateObjs == null){
            validateObjs = new Stack<>();
        }
        validateObjs.push(validateObjList);
    }

    public List<ValidateObj> peek(){
        if(validateObjs == null){
            return null;
        }
        return validateObjs.peek();
    }

    public List<ValidateObj> pop(){
        if(validateObjs == null){
            return null;
        }
        return validateObjs.pop();
    }
}
