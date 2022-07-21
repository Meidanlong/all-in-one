package com.mdl.java.base;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/18 16:33
 */
public class SwitchTest {

    public static void main(String[] args) {
        int i = 2;

        switch (i){
            case 1:
                System.out.println(1);
                break;
            case 2:
                System.out.println(2);
            case 3:
                System.out.println(3);
                break;
            default:
                System.out.println(-1);
                break;
        }
    }
}
