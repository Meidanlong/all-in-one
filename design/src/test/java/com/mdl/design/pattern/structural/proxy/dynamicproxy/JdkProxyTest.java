package com.mdl.design.pattern.structural.proxy.dynamicproxy;


import com.mdl.design.domain.Animal;
import com.mdl.design.domain.Dog;
import com.mdl.design.domain.Mammal;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 4:03 PM
 */
class JdkProxyTest {

    public static void main(String[] args) {
        JdkProxy jdkProxy = new JdkProxy(new Dog());
        Mammal dogProxy = (Mammal) jdkProxy.newProxyInstance();
        dogProxy.run();
    }

}