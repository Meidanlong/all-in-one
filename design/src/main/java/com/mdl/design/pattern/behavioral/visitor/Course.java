package com.mdl.design.pattern.behavioral.visitor;

/**
 * Created by geely
 */
public abstract class Course {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public abstract void accept(IVisitor visitor);

}
