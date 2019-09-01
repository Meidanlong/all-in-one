package com.meidl.leetcode.arrary;

/**
 * 从排序数组中删除重复项
 * <p>
 * <p>
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 示例 1:
 * <p>
 * 给定数组 nums = [1,1,2],
 * <p>
 * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。
 * <p>
 * 你不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 示例 2:
 * <p>
 * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
 * <p>
 * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
 * <p>
 * 你不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 说明:
 * <p>
 * 为什么返回数值是整数，但输出的答案是数组呢?
 * <p>
 * 请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
 * <p>
 * 你可以想象内部操作如下:
 * <p>
 * // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
 * int len = removeDuplicates(nums);
 * <p>
 * // 在函数里修改输入数组对于调用者是可见的。
 * // 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
 * for (int i = 0; i < len; i++) {
 * print(nums[i]);
 * }
 */


public class RemoveDuplicatesSolution {

    //我的解法，我觉得这个简单的题难度在于必须要原地修改数组引用，而且并不是修改了数组的长度，只是截取前多少位返回。
    public int removeDuplicates(int[] nums) {

        if(nums == null){
            return 0;
        }

        int[] nums_bak = nums;
        int index = 1;

        nums[0] = nums_bak[0];

        for(int i = 1;i < nums_bak.length ;i++){
            if(nums_bak[i] != nums_bak[i-1]){
                nums[index] = nums_bak[i];
                index ++;
            }
        }
        return index -1;
    }

    //官方思路：双指针法
    public int removeDuplicates2(int[] nums) {

        if(nums == null){
            return 0;
        }

        int i = 0;//不重复nums指针

        for(int j = 1; j<nums.length;j++){
            if(nums[i] != nums[j]){
                i++;
                nums[i] = nums[j];
            }
        }

        return i+1;
    }

}
