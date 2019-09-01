package com.meidl.leetcode.arrary;

/**
 * 移动零
 * <p>
 * <p>
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * 示例:
 * <p>
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * <p>
 * 说明:
 * <p>
 * 必须在原数组上操作，不能拷贝额外的数组。
 * 尽量减少操作次数。
 */
public class MoveZeroesSolution {

    public void moveZeroes(int[] nums) {
        int index = nums.length;//是末尾第一个不为零的数字的索引
        while(index>0 && nums[index-1] == 0 ){
            index --;
        }
        if(index > 0){
            for(int i=0; i<index; i++){
                if(nums[i] == 0){
                    for(int j=i; j<index-1; j++){
                        nums[j] = nums[j+1];
                    }
                    nums[index-1] = 0;
                    index--;
                    i--;
                }
            }
        }

    }
    public void moveZeroes1(int[] nums) {
        int zeroNum = 0;
        int index = nums.length;
        int head = 0;

         while(nums != null && index > 0 && head <= index-1 - zeroNum){
             if(zeroNum > 0){
                 nums[head] = nums[head+zeroNum];
                 if(nums[head] == 0){
                     zeroNum++;
                 }else{
                     head++;
                 }
             }else{
                 if(nums[head] == 0){
                     zeroNum++;
                 }else{
                     head++;
                 }
             }
         }

         for(int j=0;j<zeroNum;j++){
             nums[index-1] = 0;
             index--;
         }
    }

    public static void main(String[] args) {
        int[] arr = {0,1,0,3,12};
        MoveZeroesSolution moveZeroesSolution = new MoveZeroesSolution();
        moveZeroesSolution.moveZeroes1(arr);
    }

}
