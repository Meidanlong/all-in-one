package com.mdl.design.pattern.creational.prototype.abstractprototype;

/**
 * Created by meidanlong
 */
public abstract class A implements Cloneable{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
