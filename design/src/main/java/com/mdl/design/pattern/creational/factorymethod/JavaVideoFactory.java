package com.mdl.design.pattern.creational.factorymethod;

/**
 * Created by meidanlong
 */
public class JavaVideoFactory extends VideoFactory {
    @Override
    public Video getVideo() {
        return new JavaVideo();
    }
}
