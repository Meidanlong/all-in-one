package com.meidl.leetcode.editor.cn;

import com.meidl.leetcode.editor.cn.helper.TreeNode;

// [687] 最长同值路径
public class LongestUnivaluePath{
  public static void main(String[] args) {
       Solution solution = new LongestUnivaluePath().new Solution();
  }
    
//leetcode submit region begin(Prohibit modification and deletion)
// Definition for a binary tree node.
class Solution {
    int res = 0;
    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 在后序遍历的位置更新 res
        maxLen(root, root.val);
        return res;
    }

    // 定义：计算以 root 为根的这棵二叉树中，从 root 开始值为 parentVal 的最长树枝长度
    private int maxLen(TreeNode root, int parentVal) {
        if (root == null) {
            return 0;
        }
        // 利用函数定义，计算左右子树值为 root.val 的最长树枝长度
        int leftLen = maxLen(root.left, root.val);
        int rightLen = maxLen(root.right, root.val);

        // 后序遍历位置顺便更新全局变量
        // 同值路径就是左右同值树枝长度之和
        res = Math.max(res, leftLen + rightLen);
        // 如果 root 本身和上级值不同，那么整棵子树都不可能有同值树枝
        if (root.val != parentVal) {
            return 0;
        }
        // 实现函数的定义：
        // 以 root 为根的二叉树从 root 开始值为 parentVal 的最长树枝长度
        // 等于左右子树的最长树枝长度的最大值加上 root 节点本身
        return  1 + Math.max(leftLen, rightLen);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}