package com.mdl.design.pattern.creational.factorymethod;

/**
 * Created by meidanlong
 */
public class PythonVideoFactory extends VideoFactory {
    @Override
    public Video getVideo() {
        return new PythonVideo();
    }
}
