package com.mdl.tools.validate.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 7:54 PM
 */
public class ValidateProxy implements MethodInterceptor {

    /**
     * 被代理对象
     */
    private Object target;

    /**
     * 利用构造函数设置被代理对象
     *
     * @param target
     */
    public ValidateProxy(Object target) {
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
//        ValidateUtil vu = (ValidateUtil) this.target;
//        try{
//            List<ValidateObj> validateObjList = ValidateCache.peekStack();
//            for(ValidateObj vo : validateObjList){
//                if(!vu.getValid()){
//                    ValidateUtil result = (ValidateUtil) methodProxy.invokeSuper(o, objects);
//                    if(!result.getValid()){
//                        vu.setValid(false);
//                    }
//                }
//            }
//        }catch (Exception e){
//            vu.setValid(false);
//        }
//        return this.target;
//        Object result = methodProxy.invokeSuper(o, objects);
        return new ValidateUtil();
    }
}
