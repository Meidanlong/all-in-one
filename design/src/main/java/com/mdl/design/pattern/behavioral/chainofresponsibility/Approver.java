package com.mdl.design.pattern.behavioral.chainofresponsibility;

/**
 * Created by geely
 */
public abstract class Approver {
    protected Approver approver;

    public void setNextApprover(Approver approver){
        this.approver = approver;
    }
    public abstract void deploy(Course course);
}
