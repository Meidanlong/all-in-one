package com.mdl.design.pattern.behavioral.visitor;

/**
 * Created by meidanlong
 */
public interface IVisitor {

    void visit(FreeCourse freeCourse);

    void visit(CodingCourse codingCourse);


}
