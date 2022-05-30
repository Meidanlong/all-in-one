package com.mdl.tools.validate.util;

import com.mdl.common.domain.Dog;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/29 11:19
 */
class ValidateUtilTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        testValidateUtil();
    }

    private static void testValidateUtil() throws NoSuchFieldException, IllegalAccessException {
        Dog dog = new Dog();
        dog.setName("球球");
        ValidateUtil validateUtil = new ValidateUtil();
        ValidateUtil validate = (ValidateUtil) new ValidateProxy(validateUtil).newProxyInstance();
        validate.notNull("name");
        System.out.println(validate.getValid());
    }

}