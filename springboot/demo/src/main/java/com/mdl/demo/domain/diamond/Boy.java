package com.mdl.demo.domain.diamond;

import com.mdl.demo.project.diamond.annotation.ApiDoc;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/31 10:43
 */
@ApiDoc(desc = "男孩", defaultValue = "boy")
public class Boy extends Person {

    @ApiDoc(desc = "男孩姓名", defaultValue = "boy-Mdl")
    private String name;

    @ApiDoc(desc = "男孩年龄", defaultValue = "boy-28")
    private String age;

    @Override
    public String run() {
        return "Boy run";
    }

    @Override
    public String eat() {
        return "Boy eat";
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

}
