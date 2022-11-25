package com.mdl.java.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/11/25 09:47
 */
public class StreamTest {

    private static List<Integer> a = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));

    public static void main(String[] args) {

        StreamTest.a.stream().forEach(aa -> {
            System.out.println("before");
            if(aa == 3){
                return;
            }else{
                System.out.println(aa);
            }
            System.out.println("after");
        });
    }
}
