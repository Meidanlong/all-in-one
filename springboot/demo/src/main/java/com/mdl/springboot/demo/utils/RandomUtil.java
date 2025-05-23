package com.mdl.springboot.demo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 随机数工具类
 *
 * @author meidanlong
 * @date 2025年01月23日
 * @version: 1.0
 */
public class RandomUtil {

    /**
     * 固定的盐值
     */
    private static final long SALT = 12345L;

    public static <T> List<T> selectRandomElements(List<T> sourceList) {
        Random random = new Random(SALT);
        List<T> result = new ArrayList<>();

        while (!sourceList.isEmpty()) {
            // 应该保证被随机的元素个数不变
            int randomIndex = random.nextInt(sourceList.size());
            T selectedElement = sourceList.remove(randomIndex);
            result.add(selectedElement);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> sourceList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
        Random random = new Random(SALT);
        // 多次运行，结果应该保持一致
        for (int i = 0; i < 5; i++) {
            List<Integer> copy = new ArrayList<>(sourceList);
//            copy = selectRandomElements(copy);
            Collections.shuffle(copy, random);
            System.out.println("Run " + (i + 1) + ": " + copy);
        }
    }
}
