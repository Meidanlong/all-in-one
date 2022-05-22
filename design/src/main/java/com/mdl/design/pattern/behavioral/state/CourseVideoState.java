package com.mdl.design.pattern.behavioral.state;

/**
 * Created by geely
 */
public abstract class CourseVideoState {
    protected CourseVideoContext courseVideoContext;

    public void setCourseVideoContext(CourseVideoContext courseVideoContext) {
        this.courseVideoContext = courseVideoContext;
    }

    public abstract void play();
    public abstract void speed();
    public abstract void pause();
    public abstract void stop();

}
