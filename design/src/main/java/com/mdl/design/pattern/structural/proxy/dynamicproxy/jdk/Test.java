package com.mdl.design.pattern.structural.proxy.dynamicproxy.jdk;

import com.mdl.design.pattern.structural.proxy.IOrderService;
import com.mdl.design.pattern.structural.proxy.Order;
import com.mdl.design.pattern.structural.proxy.OrderServiceImpl;

/**
 * Created by meidanlong
 */
public class Test {
    public static void main(String[] args) {
        Order order = new Order();
//        order.setUserId(2);
        order.setUserId(1);
        IOrderService orderServiceDynamicProxy = (IOrderService) new OrderServiceDynamicProxy(new OrderServiceImpl()).bind();

        orderServiceDynamicProxy.saveOrder(order);
    }
}
