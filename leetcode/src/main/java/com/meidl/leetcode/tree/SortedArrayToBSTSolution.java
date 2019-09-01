package com.meidl.leetcode.tree;


import java.util.ArrayDeque;

/**
 * 将有序数组转换为二叉搜索树
 *
 *
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 *
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * 示例:
 *
 * 给定有序数组: [-10,-3,0,5,9],
 *
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 */
public class SortedArrayToBSTSolution {

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode treeNode = sortedArrayToBST(nums,0,nums.length-1);
        return treeNode;
    }

    public TreeNode sortedArrayToBST(int[] nums,int l,int r){
        if(l > r)
            return null;
        if(l == r)
            return new TreeNode(nums[l]);
        int mid = l+(r-l)/2;
        TreeNode treeNode = new TreeNode(nums[mid]);
        treeNode.left = sortedArrayToBST(nums,l,mid-1);
        treeNode.right = sortedArrayToBST(nums,mid+1,r);
        return treeNode;
    }

    public static void main(String[] args) {
        int[] arr = {0,1,2,3,4,5,6,7,8,9,10};
        SortedArrayToBSTSolution sortedArrayToBST = new SortedArrayToBSTSolution();
        TreeNode treeNode = sortedArrayToBST.sortedArrayToBST(arr);
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.addLast(treeNode);
        while(arrayDeque.size()>0){
            TreeNode t = (TreeNode) arrayDeque.pop();
            System.out.println(t.val);
            if(null != t.left)
                arrayDeque.addLast(t.left);
            if(null != t.right)
                arrayDeque.addLast(t.right);
        }
    }
}
