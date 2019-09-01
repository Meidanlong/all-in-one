package com.meidl.leetcode.arrary;


import java.util.HashMap;

/**
 * 两个数组的交集 II
 *
 *
 *给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1:
 *
 * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出: [2,2]
 *
 * 示例 2:
 *
 * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出: [4,9]
 *
 * 说明：
 *
 *     输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
 *     我们可以不考虑输出结果的顺序。
 *
 * 进阶:
 *
 *     如果给定的数组已经排好序呢？你将如何优化你的算法？
 *     如果 nums1 的大小比 nums2 小很多，哪种方法更优？
 *     如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
 */
public class IntersectSolution {

    public int[] intersect(int[] nums1, int[] nums2) {
        int[] array = new int[nums1.length>nums2.length?nums2.length:nums1.length];
        int index = 0;
        int[] result = new int[index];

        if(nums1 == null || nums1.length == 0)
            return result;
        if(nums2 == null || nums2.length == 0)
            return result;

        HashMap<Integer,Integer> map1 = mapput(nums1);
        HashMap<Integer,Integer> map2 = mapput(nums2);


        if(map1==null ||map2 == null ||map1.size() == 0||map2.size() == 0){
            return result;
        }else{

            if(map1.size()>map2.size()){
                for(Integer key : map1.keySet()){
                    if(map2.containsKey(key)){
                        int numValues = map1.get(key) > map2.get(key) ? map2.get(key): map1.get(key);
                        for(int i = 0; i<numValues; i++){
                            array[index] = key;
                            index++;
                        }
                    }
                }
            }else{
                for(Integer key : map2.keySet()){
                    if(map1.containsKey(key)){
                        int numValues = map1.get(key) > map2.get(key) ? map2.get(key): map1.get(key);
                        for(int i = 0; i<numValues; i++){
                            array[index] = key;
                            index++;
                        }
                    }
                }
            }
        }

        if(index == 0)
            return result;
        result = new int[index];
        for(int i=0;i<index;i++){
            result[i] = array[i];
        }
        return result;
    }

    public HashMap mapput(int[] nums){
        HashMap<Integer,Integer> map  = new HashMap<>();

        for(int i = 0;i<nums.length;i++){

            if(map != null && map.size() > 0){
                if(map.containsKey(nums[i])){
                    map.put(nums[i],map.get(nums[i])+1);
                }else{
                    map.put(nums[i],1);
                }
            }else
                map.put(nums[i],1);

        }
        return map;
    }

    //1ms 官方实现
    public int[] intersect2(int[] nums1, int[] nums2) {
        if(nums1.length == 0 || nums2.length == 0)   {
            return new int[0];
        }

        int[] ret1 = new int[Math.max(nums1.length, nums2.length)];
        int len1 = 0;
        boolean[] bl1 = new boolean[ret1.length];
        for (int i=0; i < nums2.length; i++) {
            int start = 0;
            while( (start = find(nums1, nums2[i], start)) != -1 ) {
                if(bl1[start] == false) {
                    ret1[len1++] = nums2[i];
                    bl1[start] = true;
                    break;
                }
                start++;
            }
        }

        int[] ret = new int[len1];
        for (int i=0; i<len1; i++) {
            ret[i] = ret1[i];
        }

        return ret;

    }

    public int find(int[] nums, int val, int i) {
        for (; i < nums.length; i++) {
            if(nums[i] == val) {
                return i;
            }
        }
        return -1;

    }


    public static void main(String[] args) {
        int[] num1 = {1,2,2,1};
        int[] num2 = {2,2};
        IntersectSolution intersectSolution = new IntersectSolution();
        int[] num3 = intersectSolution.intersect(num1,num2);
        for(int i : num3){
            System.out.println(i);
        }
    }

}