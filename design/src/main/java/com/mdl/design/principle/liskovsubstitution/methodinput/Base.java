package com.mdl.design.principle.liskovsubstitution.methodinput;

import java.util.HashMap;

/**
 * Created by meidanlong
 */
public class Base {
    public void method(HashMap map){
        System.out.println("父类被执行");
    }
}
