package com.meidl.leetcode.editor.cn;

import com.meidl.leetcode.editor.cn.helper.TreeNode;

import java.util.ArrayList;
import java.util.List;

// [144] 二叉树的前序遍历
public class BinaryTreePreorderTraversal{
  public static void main(String[] args) {
       Solution solution = new BinaryTreePreorderTraversal().new Solution();
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
    List<Integer> result = new ArrayList<>();

    public List<Integer> preorderTraversal(TreeNode root) {
        solution(root);
        return result;
    }

    void solution(TreeNode root){
        if(root == null){
            return;
        }
        result.add(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}