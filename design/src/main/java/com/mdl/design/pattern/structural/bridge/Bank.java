package com.mdl.design.pattern.structural.bridge;

/**
 * Created by meidanlong
 */
public abstract class Bank {
    protected Account account;
    public Bank(Account account){
        this.account = account;
    }
    abstract Account openAccount();

}
