package com.mdl.design.pattern.behavioral.iterator;

/**
 * Created by meidanlong.
 */
public interface CourseAggregate {

    void addCourse(Course course);
    void removeCourse(Course course);

    CourseIterator getCourseIterator();



}
