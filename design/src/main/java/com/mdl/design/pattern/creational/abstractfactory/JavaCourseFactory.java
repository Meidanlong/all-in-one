package com.mdl.design.pattern.creational.abstractfactory;

/**
 * Created by meidanlong
 */
public class JavaCourseFactory implements CourseFactory {
    @Override
    public Video getVideo() {
        return new JavaVideo();
    }

    @Override
    public Article getArticle() {
        return new JavaArticle();
    }
}
