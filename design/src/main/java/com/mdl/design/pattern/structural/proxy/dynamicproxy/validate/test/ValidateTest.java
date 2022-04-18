package com.mdl.design.pattern.structural.proxy.dynamicproxy.validate.test;

import com.mdl.design.domain.Dog;
import com.mdl.design.pattern.structural.proxy.dynamicproxy.validate.util.ValidateProxy;
import com.mdl.design.pattern.structural.proxy.dynamicproxy.validate.util.ValidateUtil;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 9:28 PM
 */
public class ValidateTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //CglibProxy cglibProxy = new CglibProxy(new Dog());
        //        Dog dogProxy = (Dog)cglibProxy.newProxyInstance();
        //        dogProxy.setName("qiuqiu");
        //        dogProxy.setName("qiuqiu1");
        //        dogProxy.run();
        //        System.out.println(dogProxy.getName());
        Dog dog = new Dog();
        dog.setName("球球");
//        ValidateUtil validate = (ValidateUtil)new ValidateUtil().init("dog", dog);
        ValidateUtil validateUtil = new ValidateUtil();
        ValidateUtil validate = (ValidateUtil) new ValidateProxy(validateUtil).newProxyInstance();
        validate.notNull("name");
        System.out.println(validate.getValid());
    }
}
