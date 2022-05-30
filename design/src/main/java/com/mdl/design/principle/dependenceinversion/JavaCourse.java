package com.mdl.design.principle.dependenceinversion;

/**
 * Created by meidanlong
 */
public class JavaCourse implements ICourse {

    @Override
    public void studyCourse() {
        System.out.println("Geely在学习Java课程");
    }
}
