package com.meidl.leetcode.arrary;


import java.util.HashMap;

/**
 * 1 两数之和
 *
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的 两个 整数。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class TwoSumSolution {

    public int[] twoSum(int[] nums, int target) {
        if(nums == null || nums.length == 0){
            return null;
        }

        int[] result = new int[2];
        for(int i=0;i<nums.length; i++){
                for(int j=0; j<nums.length; j++){
                    if(i==j)
                        continue;
                    if(nums[i]+nums[j] == target){
                        result[0] = i;
                        result[1] = j;
                        return result;
                    }
                }
        }
        return null;
    }

    public int[] twoSum1(int[] nums, int target) {
        HashMap<Integer,Integer> map = new HashMap<>();

        for(int n=0;n<nums.length;n++){
            map.put(nums[n],n);
        }

        int result[] = new int[2];
        for(int n=0;n<nums.length;n++){
            int item = nums[n];
            if(map.containsKey(target - item) && map.get(target - item) != n){
                result[0]=n;
                result[1]= map.get(target - item);
                return result;
            }
        }
        return result;

    }

    public static void main(String[] args) {
        int[] arr = {-3,4,3,90};
        TwoSumSolution twoSumSolution = new TwoSumSolution();
        for(int i:twoSumSolution.twoSum(arr,0))
            System.out.println(i);
    }
}
