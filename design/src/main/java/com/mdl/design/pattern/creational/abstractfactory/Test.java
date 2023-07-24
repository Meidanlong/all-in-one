package com.mdl.design.pattern.creational.abstractfactory;

/**
 * Created by meidanlong
 */
public class Test {
    public static void main(String[] args) {
        CourseFactory courseFactory = new JavaCourseFactory();
        Video video = courseFactory.getVideo();
        Article article = courseFactory.getArticle();
        video.produce();
        article.produce();

        courseFactory = new PythonCourseFactory();
    }
}
