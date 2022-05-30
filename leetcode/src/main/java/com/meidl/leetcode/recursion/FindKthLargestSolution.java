package com.meidl.leetcode.recursion;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *215. 数组中的第K个最大元素
 *
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * 示例 1:
 *
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * 示例 2:
 *
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * 说明:
 *
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-largest-element-in-an-array
 */
public class FindKthLargestSolution {

    public int findKthLargest(int[] nums, int k) {

        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
//                return o2-o1;
                return o1-o2;
            }
        };

//        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k,comparator);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

        for(int num : nums){
//            if (minHeap.size() < k || num > minHeap.peek())
            if (minHeap.size() < k || num > minHeap.peek())
                minHeap.offer(num);
            if (minHeap.size() > k)
                minHeap.poll();

        }

        return minHeap.peek();
    }

    //第k大的元素，首先要维护一个长度为k的最小堆，对顶就是第k大的元素

    public void heapify(int[] heap){}


    public static void main(String[] args) {
        int[] a = new int[100];

        for(int i=0; i<100; i++){
            a[i] = i+1 ;
        }

        FindKthLargestSolution findKthLargestSolution = new FindKthLargestSolution();

        System.out.println(findKthLargestSolution.findKthLargest(a,46));
    }
}
