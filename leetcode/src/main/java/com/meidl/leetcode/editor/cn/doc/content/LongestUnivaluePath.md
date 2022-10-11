<p>给定一个二叉树的
 <meta charset="UTF-8" />&nbsp;<code>root</code>&nbsp;，返回&nbsp;<em>最长的路径的长度</em> ，这个路径中的&nbsp;<em>每个节点具有相同值</em>&nbsp;。 这条路径可以经过也可以不经过根节点。</p>

<p><strong>两个节点之间的路径长度</strong>&nbsp;由它们之间的边数表示。</p>

<p>&nbsp;</p>

<p><strong>示例 1:</strong></p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2020/10/13/ex1.jpg" /></p>

<pre>
<strong>输入：</strong>root = [5,4,5,1,1,5]
<strong>输出：</strong>2
</pre>

<p><strong>示例 2:</strong></p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2020/10/13/ex2.jpg" /></p>

<pre>
<strong>输入：</strong>root = [1,4,5,4,4,5]
<strong>输出：</strong>2
</pre>

<p>&nbsp;</p>

<p><strong>提示:</strong></p>

<ul> 
 <li>树的节点数的范围是
  <meta charset="UTF-8" />&nbsp;<code>[0, 10<sup>4</sup>]</code>&nbsp;</li> 
 <li><code>-1000 &lt;= Node.val &lt;= 1000</code></li> 
 <li>树的深度将不超过 <code>1000</code>&nbsp;</li> 
</ul>

<details><summary><strong>Related Topics</strong></summary>树 | 深度优先搜索 | 二叉树</details><br>

<div>👍 737, 👎 0</div>

<div id="labuladong"><hr>

**通知：[数据结构精品课](https://aep.h5.xeknow.com/s/1XJHEO) 已更新到 V2.0，第 12 期刷题打卡 [开始报名](https://aep.xet.tech/s/XhcRc)，点击这里体验 [刷题全家桶](https://labuladong.gitee.io/algo/images/others/%E5%85%A8%E5%AE%B6%E6%A1%B6.jpg)。**

<details><summary><strong>labuladong 思路</strong></summary>

## 基本思路

前文 [手把手刷二叉树总结篇](https://labuladong.github.io/article/fname.html?fname=二叉树总结) 说过二叉树的递归分为「遍历」和「分解问题」两种思维模式，这道题需要用到「分解问题」的思维，而且这类题目需要利用二叉树的后序遍历。

做这题之前，我建议你先做 [543. 二叉树的直径](/problems/diameter-of-binary-tree) 题并进行对比，把那道题的最大深度函数 `maxDepth` 的定义带入到这道题中，`maxLen` 相当于求值为 `parentVal` 的节点的最大深度。配合代码注释就立马明白了。

**标签：[二叉树](https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzAxODQxMDM0Mw==&action=getalbum&album_id=2121994699837177859)，后序遍历**

## 解法代码

```java
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
```

**类似题目**：
  - [814. 二叉树剪枝 🟠](/problems/binary-tree-pruning)
  - [剑指 Offer II 047. 二叉树剪枝 🟠](/problems/pOCWxh)

</details>
</div>



