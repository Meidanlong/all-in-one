package com.mdl.design.pattern.structural.adapter.classadapter;

/**
 * Created by meidanlong
 */
public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("concreteTarget目标方法");
    }

}
