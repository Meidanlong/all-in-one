package com.mdl.design.pattern.behavioral.visitor;

/**
 * Created by geely
 */
public class FreeCourse extends Course {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
