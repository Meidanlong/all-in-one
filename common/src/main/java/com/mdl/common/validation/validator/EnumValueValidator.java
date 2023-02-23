package com.mdl.common.validation.validator;


import com.mdl.common.validation.annotation.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/9/5 11:32
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends Enum<?>> enumClass;

    private String enumMethod;

    @Override
    public void initialize(EnumValue enumValue) {
        this.enumMethod = enumValue.enumMethod();
        this.enumClass = enumValue.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null || enumClass == null || enumMethod == null) {
            return true;
        }

        Class<?> valueClass = value.getClass();

        try {
            Method method = enumClass.getMethod(enumMethod, valueClass);
            if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                throw new RuntimeException(String.format("%s method return is not boolean type in the %s class",
                        enumMethod, enumClass));
            }

            if(!Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException(String.format("%s method is not static method in the %s class",
                        enumMethod, enumClass));
            }

            Boolean result = (Boolean)method.invoke(null, value);
            return result != null && result;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(String.format("This %s(%s) method does not exist in the %s", enumMethod,
                    valueClass, enumClass), e);
        }
    }
}
