package com.mdl.design.pattern.structural.proxy.dynamicproxy.validate.util;

import com.mdl.design.pattern.structural.proxy.dynamicproxy.validate.domain.ValidateObj;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 9:09 PM
 */
public class ValidateHelper {

    public static Object getFieldObj(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
