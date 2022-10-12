package com.mdl.springboot.demo.domain.diamond;

import com.mdl.common.domain.Mammal;
import com.mdl.springboot.demo.project.diamond.annotation.ApiDoc;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/31 10:43
 */
@ApiDoc(desc = "人", defaultValue = "Men")
public class Person implements Mammal {

    @ApiDoc(desc = "姓名", defaultValue = "Mdl")
    private String name;

    @ApiDoc(desc = "年龄", defaultValue = "28")
    private String age;

    @ApiDoc(desc = "男孩", defaultValue = "boy")
    private Boy boy;

    @Override
    public String run() {
        return "Person run";
    }

    @Override
    public String eat() {
        return "Person eat";
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
}
