package com.mdl.design.principle.dependenceinversion;

/**
 * Created by meidanlong
 */
public class PythonCourse implements ICourse {
    @Override
    public void studyCourse() {
        System.out.println("Geely在学习Python课程");
    }
}
