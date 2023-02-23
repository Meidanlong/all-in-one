package com.meidl.leetcode.editor.cn;

import com.meidl.leetcode.editor.cn.helper.TreeNode;

/**
 *  [104] 二叉树的最大深度
 *
 *  二叉树深度遍历方式有两种：
 *  1、遍历二叉树，不断比较深度，取最大值
 *  - 进入当前节点时深度+1
 *  - 退出当前节点时深度-1
 *  - 如果当前节点时叶子节点时，比较当前路径深度与最大路径深度，若大于最大深度则替换最大深度值
 *
 *  2、递归求子树最大深度，当前节点最大深度为子树最大深度+1 (并不需要记录最大值)
 *  - 如果当前节点为叶子节点，则深度为1
 *  - 如果当前节点不为叶子节点，则深度=子树最大深度+1
 */
public class MaximumDepthOfBinaryTree{
  public static void main(String[] args) {
       Solution solution = new MaximumDepthOfBinaryTree().new Solution();
  }
    
//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int maxDepth(TreeNode root) {
        solution1(root, 0);
        return maxDepth;
//        return solution2(root);
    }

    private int maxDepth;
    private void solution1(TreeNode node, int depth){
        if(node == null){
            return;

        }
        depth++;
        maxDepth = Math.max(depth, maxDepth);
        solution1(node.left, depth);
        solution1(node.right, depth);
        depth--;
    }

    private int solution2(TreeNode node){
        // 如果是叶子节点
        if(node == null){
            return 0;
        }
        if(node.left == node.right){
            return 1;
        }
        return Math.max(solution2(node.left)+1, solution2(node.right)+1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}