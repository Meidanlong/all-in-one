package com.mdl.design.pattern.creational.abstractfactory;

/**
 * Created by meidanlong
 */
public class JavaArticle extends Article {
    @Override
    public void produce() {
        System.out.println("编写Java课程手记");
    }
}
