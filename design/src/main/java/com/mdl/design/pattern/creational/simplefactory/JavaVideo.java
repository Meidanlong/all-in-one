package com.mdl.design.pattern.creational.simplefactory;

/**
 * Created by meidanlong
 */
public class JavaVideo extends Video {
    @Override
    public void produce() {
        System.out.println("录制Java课程视频");
    }
}
