package com.mdl.design.pattern.structural.adapter.objectadapter;

/**
 * Created by geely
 */
public class Adapter implements Target{
    private Adaptee adaptee = new Adaptee();

    @Override
    public void request() {
        //...
        adaptee.adapteeRequest();
        //...
    }
}
