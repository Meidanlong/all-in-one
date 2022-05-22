package com.mdl.design.pattern.behavioral.visitor;

/**
 * Created by geely
 */
public interface IVisitor {

    void visit(FreeCourse freeCourse);

    void visit(CodingCourse codingCourse);


}
