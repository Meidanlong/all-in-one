package com.mdl.design.pattern.behavioral.state;

/**
 * Created by meidanlong
 */
public class Test {
    public static void main(String[] args) {
        CourseVideoContext courseVideoContext = new CourseVideoContext();
        courseVideoContext.setCourseVideoState(new PlayState());

        System.out.println("当前状态:"+courseVideoContext.getCourseVideoState().getClass().getSimpleName());
        courseVideoContext.pause();

        System.out.println("当前状态:"+courseVideoContext.getCourseVideoState().getClass().getSimpleName());

        courseVideoContext.speed();

        System.out.println("当前状态:"+courseVideoContext.getCourseVideoState().getClass().getSimpleName());

        courseVideoContext.stop();

        System.out.println("当前状态:"+courseVideoContext.getCourseVideoState().getClass().getSimpleName());


        courseVideoContext.speed();

    }
}
