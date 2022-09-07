package com.mdl.tools.validation.cglib;

import java.util.List;
import java.util.Stack;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:36 PM
 */
class ValidateStruct extends ValidateMsg {

    private Stack<List<ObjectValidateImpl>> stack = new Stack<>();

    protected Boolean stackIsEmpty(){
        return stack.isEmpty();
    }

    protected List<ObjectValidateImpl> peekValidateObjectList(){
        return stack.peek();
    }

    protected List<ObjectValidateImpl> popValidateObjectList(){
        return stack.pop();
    }

    protected void pushValidateObjectList(List<ObjectValidateImpl> validateObjectList){
        stack.push(validateObjectList);
    }

    protected void addValidateObjectList(List<ObjectValidateImpl> validateObjectList){
        List<ObjectValidateImpl> oriValidateObjectList = peekValidateObjectList();
        if(oriValidateObjectList == null){
            pushValidateObjectList(validateObjectList);
        }else{
            oriValidateObjectList.addAll(validateObjectList);
        }
    }

}
