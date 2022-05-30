package com.mdl.design.pattern.structural.adapter.classadapter;

/**
 * Created by meidanlong
 */
public class Adapter extends Adaptee implements Target{
    @Override
    public void request() {
        //...
        super.adapteeRequest();
        //...
    }
}
