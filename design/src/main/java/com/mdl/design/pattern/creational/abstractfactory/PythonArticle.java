package com.mdl.design.pattern.creational.abstractfactory;

/**
 * Created by meidanlong
 */
public class PythonArticle extends Article {
    @Override
    public void produce() {
        System.out.println("编写Python课程手记");
    }
}
