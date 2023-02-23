<p>定义一个函数&nbsp;<code>f(s)</code>，统计&nbsp;<code>s</code> &nbsp;中<strong>（按字典序比较）最小字母的出现频次</strong> ，其中 <code>s</code>&nbsp;是一个非空字符串。</p>

<p>例如，若&nbsp;<code>s = "dcce"</code>，那么&nbsp;<code>f(s) = 2</code>，因为字典序最小字母是&nbsp;<code>"c"</code>，它出现了&nbsp;2 次。</p>

<p>现在，给你两个字符串数组待查表&nbsp;<code>queries</code>&nbsp;和词汇表&nbsp;<code>words</code> 。对于每次查询&nbsp;<code>queries[i]</code> ，需统计 <code>words</code> 中满足&nbsp;<code>f(queries[i])</code>&nbsp;&lt; <code>f(W)</code>&nbsp;的<strong> 词的数目</strong> ，<code>W</code> 表示词汇表&nbsp;<code>words</code>&nbsp;中的每个词。</p>

<p>请你返回一个整数数组&nbsp;<code>answer</code>&nbsp;作为答案，其中每个&nbsp;<code>answer[i]</code>&nbsp;是第 <code>i</code> 次查询的结果。</p>

<p>&nbsp;</p>

<p><strong>示例 1：</strong></p>

<pre>
<strong>输入：</strong>queries = ["cbd"], words = ["zaaaz"]
<strong>输出：</strong>[1]
<strong>解释：</strong>查询 f("cbd") = 1，而 f("zaaaz") = 3 所以 f("cbd") &lt; f("zaaaz")。
</pre>

<p><strong>示例 2：</strong></p>

<pre>
<strong>输入：</strong>queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
<strong>输出：</strong>[1,2]
<strong>解释：</strong>第一个查询 f("bbb") &lt; f("aaaa")，第二个查询 f("aaa") 和 f("aaaa") 都 &gt; f("cc")。
</pre>

<p>&nbsp;</p>

<p><strong>提示：</strong></p>

<ul> 
 <li><code>1 &lt;= queries.length &lt;= 2000</code></li> 
 <li><code>1 &lt;= words.length &lt;= 2000</code></li> 
 <li><code>1 &lt;= queries[i].length, words[i].length &lt;= 10</code></li> 
 <li><code>queries[i][j]</code>、<code>words[i][j]</code> 都由小写英文字母组成</li> 
</ul>

<details><summary><strong>Related Topics</strong></summary>数组 | 哈希表 | 字符串 | 二分查找 | 排序</details><br>

<div>👍 58, 👎 0<span style='float: right;'><span style='color: gray;'><a href='https://github.com/labuladong/fucking-algorithm/discussions/939' target='_blank' style='color: lightgray;text-decoration: underline;'>bug 反馈</a> | <a href='https://mp.weixin.qq.com/s/NF8mmVyXVfC1ehdMOsO7Cw' target='_blank' style='color: lightgray;text-decoration: underline;'>使用指南</a> | <a href='https://labuladong.github.io/algo/images/others/%E5%85%A8%E5%AE%B6%E6%A1%B6.jpg' target='_blank' style='color: lightgray;text-decoration: underline;'>更多配套插件</a></span></span></div>

<div id="labuladong"><hr>

**通知：[数据结构精品课](https://aep.h5.xeknow.com/s/1XJHEO) 已更新到 V2.1，[手把手刷二叉树系列课程](https://aep.xet.tech/s/3YGcq3) 上线，[第 17 期刷题打卡挑战](https://aep.xet.tech/s/2jPp5X) 下周开始，报名从速！。**

</div>



