package com.meidl.leetcode.tree;

/**
 * 104. 二叉树的最大深度
 *
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
 */
public class MaxDepthSolution {

    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
    int maxDepth = 0;

    public int maxDepth(TreeNode root) {

        maxDepth(0,root);

        return maxDepth;

    }

    private void maxDepth(int depth,TreeNode node){
        //递归到底
        if(node == null){
            maxDepth = depth>maxDepth?depth:maxDepth;
        }else{
            int newdepth = ++depth;
            maxDepth(newdepth,node.left);
            maxDepth(newdepth,node.right);
        }
    }


    public static void main(String[] args) {
        TreeNode t = new TreeNode(3);
        t.left = new TreeNode(9);
        t.right = new TreeNode(20);
        t.left.left = null;
        t.left.right = null;
        t.right.left= new TreeNode(15);
        t.right.right = new TreeNode(7);
        System.out.println(new MaxDepthSolution().maxDepth(t));
    }

    static int n = 0;
    public static int maxDepth(TreeNode t,TreeNode s) {
        if(t.left!= null && t.right!=null){
            if(t.left != null){
                n++;
                maxDepth(t.left,s);
            }else if(t.right!=null){
                n++;
                maxDepth(t.right,s);
            }
            maxDepth(s.right, s);

        }
        //maxDepth(s.right);

        //maxDepth(t.right);

        return n ;

    }
}
