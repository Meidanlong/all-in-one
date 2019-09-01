package com.meidl.leetcode.arrary;


/**
 * 加一
 *
 *
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储一个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 * 示例 1:
 *
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * 解释: 输入数组表示数字 123。
 *
 * 示例 2:
 *
 * 输入: [4,3,2,1]
 * 输出: [4,3,2,2]
 * 解释: 输入数组表示数字 4321。
 */
public class PlusOneSolution {

    public int[] plusOne(int[] digits) {
        return plusone(digits,digits.length);
    }

    public int[] plusone(int[] arr,int index){
        if(index == 0){
            int[] result = new int[arr.length+1];
            result[0] = 1;
            return  result;
        }

        int num = arr[index-1]+1;
        if(num > 9){
            arr[index-1] = 0;
            index--;
            return plusone(arr,index);
        }else{
            arr[index-1] = num;
            return arr;
        }
    }

    public int[] plusOne1(int[] digits) {
        int index = digits.length;
        boolean isContinue = true;
        while(index >= 0 && isContinue){
            if(index == 0){
                int[] result = new int[digits.length+1];
                result[0] = 1;
                return  result;
            }

            int num = digits[index-1] + 1;
            if(num > 9){
                digits[--index] = 0;
                isContinue = true;
            }else{
                digits[index-1] = num;
                isContinue = false;
            }
        }
        return digits;
    }

    public static void main(String[] args) {

        int[] arr = {1,2,3};
//        int[] arr = {9,9};
        PlusOneSolution plusOneSolution = new PlusOneSolution();
        for(int i : plusOneSolution.plusOne1(arr))
            System.out.println(i);

    }
}
