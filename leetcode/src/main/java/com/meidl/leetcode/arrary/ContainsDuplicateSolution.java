package com.meidl.leetcode.arrary;


import java.util.TreeSet;

/**
 * 存在重复
 * <p>
 * <p>
 * 给定一个整数数组，判断是否存在重复元素。
 * <p>
 * 如果任何值在数组中出现至少两次，函数返回 true。如果数组中每个元素都不相同，则返回 false。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,2,3,1]
 * 输出: true
 * <p>
 * 示例 2:
 * <p>
 * 输入: [1,2,3,4]
 * 输出: false
 * <p>
 * 示例 3:
 * <p>
 * 输入: [4,3,4,3,0,4,3,2,4,2]
 * 输出: true
 */
public class ContainsDuplicateSolution {

    public boolean containsDuplicate(int[] nums) {

        if (nums == null || nums.length == 0) {
            return false;
        }

        TreeSet<Integer> treeSet = new TreeSet();

        for (int i = 0; i < nums.length; i++) {
            if (!treeSet.contains(nums[i])) {
                treeSet.add(nums[i]);
            } else
                return true;
        }
        return false;
    }

    //
    public boolean containsDuplicate2(int[] nums) {
        for(int i = 0;i<nums.length;i++){
            //for(int j=i+1;j<nums.length;j++){//如果是j++每次遍历的元素是从多到少的，j--每次遍历的元素是从少到多的
            for(int j=i-1;j>=0;j--){
                if (nums[i] > nums[j]) break;
                else if (nums[i] == nums[j]) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = {2,1,2};
        ContainsDuplicateSolution containsDuplicateSolution = new ContainsDuplicateSolution();
        System.out.println(containsDuplicateSolution.containsDuplicate2(arr));
    }
}