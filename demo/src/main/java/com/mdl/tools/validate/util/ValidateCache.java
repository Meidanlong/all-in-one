package com.mdl.tools.validate.util;

import com.mdl.tools.validate.domain.ValidateObj;
import com.mdl.tools.validate.domain.ValidateStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 9:00 PM
 */
public class ValidateCache {

    private static ThreadLocal<ValidateStack> stack = ThreadLocal.withInitial(ValidateStack::new);

    public static void initCache(String name, Object obj){
        pushObjAsList(name, obj);
    }

    public static List<ValidateObj> peekStack(){
        return stack.get().peek();
    }

    private static void pushObjAsList(String name, Object obj){
        List<ValidateObj> validateObjList = new ArrayList<>();
        if(obj instanceof Collection){
            Collection col = (Collection) obj;
            int colIndex = 0;
            Iterator colIterator = col.iterator();
            while (colIterator.hasNext()){
                Object theObj = colIterator.next();
                String theName = name + "_" + colIndex++;
                ValidateObj validateObj = new ValidateObj();
                validateObj.setCurObj(theObj);
                validateObj.setObjName(theName);
                validateObjList.add(validateObj);
            }
        }else{
            ValidateObj validateObj = new ValidateObj();
            validateObj.setCurObj(obj);
            validateObj.setObjName(name);
            validateObjList.add(validateObj);
        }
        stack.get().push(validateObjList);
    }
}
