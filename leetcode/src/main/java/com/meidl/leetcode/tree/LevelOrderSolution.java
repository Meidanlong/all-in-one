package com.meidl.leetcode.tree;


import java.util.*;

/**
 * 二叉树的层次遍历
 *
 * 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 * 例如:
 * 给定二叉树: [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回其层次遍历结果：
 *
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 */
public class LevelOrderSolution {
    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
     TreeNode(int x) { val = x; }
    }

    private Map<Integer,List<Integer>> map = new HashMap<>();
    private int maxDepth = 0;//记录最大深度

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lists = new LinkedList<>();
        if(root == null) {
            return lists;
        }
        levelOrder(0,root);

        for(int i=0; i<=maxDepth; i++){//从第0层开始一层一层遍历，直到最大深度
            lists.add(map.get(i));
        }

        return lists;

    }

    private void levelOrder(int i, TreeNode root) {
        if(root != null){
            maxDepth = maxDepth>i?maxDepth:i;
            List<Integer> listI = new LinkedList<>();
            if(null!=map.get(i)){//如果map没有这个key（也就是没有这一层的数），就新增
                listI = map.get(i);
            }
            listI.add(root.val);
            map.put(i,listI);
            int nextLevel = ++i;
            levelOrder(nextLevel,root.left);
            levelOrder(nextLevel,root.right);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.left.left = null;
        root.right.right = null;
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);


        LevelOrderSolution levelOrderSolution = new LevelOrderSolution();
        levelOrderSolution.levelOrder2(root);
    }

    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }

    public void dfs(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) return;
        if (result.size() < level + 1) result.add(new ArrayList<>());
        result.get(level).add(node.val);
        dfs(node.left, level + 1, result);
        dfs(node.right, level + 1, result);
    }
}
