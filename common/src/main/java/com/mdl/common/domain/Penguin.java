package com.mdl.common.domain;

import lombok.Data;

/**
 * @description:企鹅
 * @author: meidanlong
 * @date: 2022/4/17 3:47 PM
 */
@Data
public class Penguin implements Bird{
    @Override
    public String run() {
        return "Penguin run";
    }

    @Override
    public String eat() {
        return "Penguin eat";
    }

    @Override
    public String fly() {
        return "Penguin can't fly";
    }
}
