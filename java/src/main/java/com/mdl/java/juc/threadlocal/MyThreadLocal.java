package com.mdl.java.juc.threadlocal;

import com.mdl.common.domain.demo.Dog;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/28 16:37
 */
public class MyThreadLocal {

    InheritableThreadLocal<Dog> myITL;

    public MyThreadLocal() {
        this.myITL = new InheritableThreadLocal<>();
    }

    public Dog getMyITL(){
        Dog dog = myITL.get();
        if(dog == null) {
            dog = new Dog();
        }
        return dog;
    }

    public void setMyITL(Dog dog) {
        if(dog != null){
            myITL.set(dog);
        }
    }

}
