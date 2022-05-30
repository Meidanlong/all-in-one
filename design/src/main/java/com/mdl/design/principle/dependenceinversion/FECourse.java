package com.mdl.design.principle.dependenceinversion;

/**
 * Created by meidanlong
 */
public class FECourse implements ICourse {
    @Override
    public void studyCourse() {
        System.out.println("Geely在学习FE课程");
    }

}
