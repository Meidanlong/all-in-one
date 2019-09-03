package com.meidl.javabasic.string;

public class EqualsDemo {


    /*public boolean equals(Object anObject) {
        // 如果比较的是对象的话，是按照"=="来比较的
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
        // 如果两个对象都是String的话，在判断字符串长度相等之后会按照字符来比较
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }*/

    public static void main(String[] args) {

        Object q1 = new String();
        Object q2 = new Object();
        System.out.println(q1.equals(q2));
    }
}
