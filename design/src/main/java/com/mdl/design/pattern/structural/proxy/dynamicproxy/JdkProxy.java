package com.mdl.design.pattern.structural.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 3:54 PM
 */
public class JdkProxy implements InvocationHandler {

    /**
     * 被代理目标对象
     */
    private Object target;

    /**
     * 利用构造函数设置被代理类属性
     *
     * @param target
     */
    public JdkProxy(Object target) {
        this.target = target;
    }

    /**
     * 创建一个代理对象
     *
     * @return
     */
    public Object newProxyInstance() {

        ClassLoader classLoader = this.target.getClass().getClassLoader();
        /**
         * 获取被代理类的接口
         */
        Class<?>[] interfaces = this.target.getClass().getInterfaces();
        /**
         * 创建代理对象
         */
        return Proxy.newProxyInstance(classLoader, interfaces, this);
    }

    /**
     * 调用代理对象的方法
     *
     * @param proxy 动态生成的代理类
     * @param method 要被增强的方法
     * @param args 方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void before(){
        System.out.println("JDK动态代理-方法前调用");
    }

    private void after(){
        System.out.println("JDK动态代理-方法后调用");
    }

}
