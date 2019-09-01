package com.meidl.leetcode.tree;


import java.util.Stack;

/**
 * 98. 验证二叉搜索树
 *
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 假设一个二叉搜索树具有如下特征：
 *
 *     节点的左子树只包含小于当前节点的数。
 *     节点的右子树只包含大于当前节点的数。
 *     所有左子树和右子树自身必须也是二叉搜索树。
 *
 * 示例 1:
 *
 * 输入:
 *     2
 *    / \
 *   1   3
 * 输出: true
 *
 * 示例 2:
 *
 * 输入:
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 * 输出: false
 * 解释: 输入为: [5,1,4,null,null,3,6]。
 *      根节点的值为 5 ，但是其右子节点值为 4 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/validate-binary-search-tree
 */

public class IsValidBSTSolution {

    public static class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
    }

    public boolean isValidBST(TreeNode root) {


        return isValidBST(root,null,null);
    }



    public boolean isValidBST(TreeNode root,Integer min,Integer max) {
        //递归到底的情况
        if(root == null) return true;
        int value = root.val;
        if(min!=null && min>=value) return false;
        if(max!=null && max<=value) return false;


        //正常情况
        if(!isValidBST(root.left,min,value)) return false;
        if(!isValidBST(root.left,value,max)) return false;

        return true;

    }

    public boolean isValidBST2(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        double inorder = - Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // If next element in inorder traversal
            // is smaller than the previous one
            // that's not BST.
            if (root.val <= inorder) return false;
            inorder = root.val;
            root = root.right;
        }
        return true;
    }

    public boolean isValidBST3(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        double inorder = - Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // If next element in inorder traversal
            // is smaller than the previous one
            // that's not BST.
            if (root.val <= inorder) return false;
            inorder = root.val;
            root = root.right;
        }
        return true;
    }


    public static void main(String[] args) {
        TreeNode t = new TreeNode(10);
        t.left = new TreeNode(5);
        t.right = new TreeNode(15);
        t.left.left = null;
        t.left.right = null;
        t.right.left= new TreeNode(6);
        t.right.right = new TreeNode(20);

        System.out.println(new IsValidBSTSolution().isValidBST2(t));
    }
}
