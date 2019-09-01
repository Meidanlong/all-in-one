package com.meidl.leetcode.arrary;


/**
 * 只出现一次的数字
 * <p>
 * <p>
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * <p>
 * 说明：
 * <p>
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * <p>
 * 示例 1:
 * <p>
 * 输入: [2,2,1]
 * 输出: 1
 * <p>
 * 示例 2:
 * <p>
 * 输入: [4,1,2,1,2]
 * 输出: 4
 */
public class SingleNumberSolution {

    public int singleNumber(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            boolean flag = false;//false表示没有重复的元素，有则置为true
            for (int j = 0; j < nums.length; j++) {
                if(i != j){
                    if (nums[i] == nums[j]) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                return nums[i];
            }
        }

        return -1;
    }

    /**
     * 官方思路：异或，相同为真，不同为假
     *
     *
     true    &=    true    ==>    true
     true    &=    false    ==>    false
     false    &=    true    ==>    false
     false    &=    false    ==>    false

     true    |=    true    ==>    true
     true    |=    false    ==>    true
     false    |=    true    ==>    true
     false    |=    false    ==>    false

     ^=  相同为假0，不同为真1
     true    ^=    true    ==>    false
     true    ^=    false    ==>    true
     false    ^=    true    ==>    true
     false    ^=    false    ==>    false

     0^=2 --> 2
     2^=2 --> 0

     3^=2 --> 5
     5^=4 --> 9
     9^=2 --> 7
     7^=7 --> 0

     */
    public int singleNumber2(int[] nums) {
            int len=nums.length;
            int res=0;
            for(int i=0;i<len;i++){
                res^=nums[i];
            }
            return res;
    }



    public static void main(String[] args) {
        int[] arr = {4,1,2,1,2};
        SingleNumberSolution singleNumberSolution = new SingleNumberSolution();
        System.out.println(singleNumberSolution.singleNumber2(arr));
    }
}
