package com.meidl.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 101. 对称二叉树
 *
 *
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 * 说明:
 *
 * 如果你可以运用递归和迭代两种方法解决这个问题，会很加分。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/symmetric-tree
 */
public class IsSymmetricSolution {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    List list = new ArrayList<Integer>() ;
    public boolean isSymmetric(TreeNode root) {

        isSymmetricRecursion(root);

        int times = list.size()/2;

        for(int i=0; i<times; i++){
            int headIndex = (int) list.get(i);
            int tailIndex = (int) list.get(list.size()-1-i);
            if(headIndex != tailIndex) return false;
        }
        return true;
    }

    private void isSymmetricRecursion(TreeNode root){
        if(root != null){
            isSymmetricRecursion(root.left);
            list.add(root.val);
            isSymmetricRecursion(root.right);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(2);
//        root.left.right = new TreeNode(3);
        root.left.right = null;
        root.right.left = new TreeNode(2);
        //root.right.right = new TreeNode(3);


        IsSymmetricSolution isSymmetricSolution = new IsSymmetricSolution();
        System.out.println(isSymmetricSolution.isSymmetric2(root));
    }

    public boolean isSymmetric2(TreeNode root) {
        return isSymmetric2Recursion(root,root);
    }

    private boolean isSymmetric2Recursion(TreeNode rootLeft,TreeNode rootRight){
        if(rootLeft == null && rootRight == null) return true;
        //两值不同时为null
        if(rootLeft == null || rootRight == null) return false;

        return (rootLeft.val == rootRight.val)
                //若不符合条件则直接短路返回false
                && isSymmetric2Recursion(rootLeft.right, rootRight.left)
                && isSymmetric2Recursion(rootLeft.left, rootRight.right);
    }

    public boolean isSymmetric3(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode t1 = q.poll();
            TreeNode t2 = q.poll();
            if (t1 == null && t2 == null) continue;
            if (t1 == null || t2 == null) return false;
            if (t1.val != t2.val) return false;
            q.add(t1.left);
            q.add(t2.right);
            q.add(t1.right);
            q.add(t2.left);
        }
        return true;
    }

}
