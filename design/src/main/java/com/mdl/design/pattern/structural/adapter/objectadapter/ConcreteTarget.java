package com.mdl.design.pattern.structural.adapter.objectadapter;


/**
 * Created by meidanlong
 */
public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("concreteTarget目标方法");
    }

}
