package com.meidl.leetcode.editor.cn.helper;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/21 10:07
 */
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
