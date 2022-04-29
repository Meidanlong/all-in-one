package com.mdl.design.pattern.structural.proxy.dynamicproxy;


import com.mdl.common.domain.Dog;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 6:09 PM
 */
class CglibProxyTest {

    public static void main(String[] args) {
//        CglibProxy cglibProxy = new CglibProxy(new Dog());
//        Dog dogProxy = (Dog)cglibProxy.newProxyInstance();
//        dogProxy.setName("qiuqiu");
//        dogProxy.setName("qiuqiu1");
//        dogProxy.run();
//        System.out.println(dogProxy.getName());


        DogCglibProxy dogCglibProxy = new DogCglibProxy();
        Dog dog = dogCglibProxy.newProxyInstance();
        dog.run();
    }

}