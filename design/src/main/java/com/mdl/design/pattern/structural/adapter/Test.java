package com.mdl.design.pattern.structural.adapter;

/**
 * Created by meidanlong
 */
public class Test {
    public static void main(String[] args) {
        DC5 dc5 = new PowerAdapter();
        dc5.outputDC5V();

    }
}
