package com.mdl.design.pattern.structural.proxy.dynamicproxy;

import com.mdl.design.domain.Dog;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 6:05 PM
 */
public class DogCglibProxy implements MethodInterceptor {

    /**
     * 被代理对象
     */
    private Dog target;

    /**
     * 利用构造函数设置被代理对象
     */
    public DogCglibProxy() {
        this.target = new Dog();
    }

    /**
     * 创建代理对象
     *
     * @return
     */
    public Dog newProxyInstance() {
        /**
         * 创建Enhancer实例
         */
        Enhancer enhancer = new Enhancer();
        /**
         * 设置被代理类
         */
        enhancer.setSuperclass(this.target.getClass());
        /**
         * 设置回调
         */
        enhancer.setCallback(this);
        /**
         * 创建代理
         */
        return (Dog)enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return "Dog run";
    }

    private void before(){
        System.out.println("Cglib动态代理-方法前调用");
    }

    private void after(){
        System.out.println("Cglib动态代理-方法后调用");
    }
}
