package com.mdl.design.pattern.structural.proxy.dynamicproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 6:05 PM
 */
public class CglibProxy implements MethodInterceptor {

    /**
     * 被代理对象
     */
    private Object target;

    /**
     * 利用构造函数设置被代理对象
     *
     * @param target
     */
    public CglibProxy(Object target) {
        this.target = target;
    }

    /**
     * 创建代理对象
     *
     * @return
     */
    public Object newProxyInstance() {
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
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    private void before(){
        System.out.println("Cglib动态代理-方法前调用");
    }

    private void after(){
        System.out.println("Cglib动态代理-方法后调用");
    }
}
