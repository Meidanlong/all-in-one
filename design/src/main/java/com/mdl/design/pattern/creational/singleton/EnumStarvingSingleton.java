package com.mdl.design.pattern.creational.singleton;

/**
 * @description: 枚举饿汉式
 * @author meidanlong
 * @date 2023年07月24日
 * @version: 1.0
 */
public class EnumStarvingSingleton {
    private EnumStarvingSingleton(){}

    private EnumStarvingSingleton getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private EnumStarvingSingleton instance;
        ContainerHolder() {
            instance = new EnumStarvingSingleton();
        }

    }
}
