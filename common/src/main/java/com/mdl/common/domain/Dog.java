package com.mdl.common.domain;

import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/17 3:50 PM
 */
@Data
public class Dog implements Mammal{

    private String name;

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
