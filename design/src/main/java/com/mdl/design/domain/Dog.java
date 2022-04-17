package com.mdl.design.domain;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 3:50 PM
 */
public class Dog implements Mammal{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String run() {
        String result = "Dog run";
        System.out.println(result);
        return result;
    }

    @Override
    public String eat() {
        return "Dog eat";
    }
}
