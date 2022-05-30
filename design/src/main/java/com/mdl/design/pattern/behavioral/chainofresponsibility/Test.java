package com.mdl.design.pattern.behavioral.chainofresponsibility;

/**
 * Created by meidanlong
 */
public class Test {
    public static void main(String[] args) {
        Approver articleApprover = new ArticleApprover();
        Approver videoApprover = new VideoApprover();

        Course course = new Course();
        course.setName("Java设计模式精讲 -- By Geely");
        course.setArticle("Java设计模式精讲的手记");
        course.setVideo("Java设计模式精讲的视频");

        articleApprover.setNextApprover(videoApprover);

        articleApprover.deploy(course);
    }
}
