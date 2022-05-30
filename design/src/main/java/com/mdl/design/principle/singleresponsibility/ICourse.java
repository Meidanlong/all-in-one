package com.mdl.design.principle.singleresponsibility;

/**
 * Created by meidanlong
 */
public interface ICourse {
    String getCourseName();
    byte[] getCourseVideo();

    void studyCourse();
    void refundCourse();

}
