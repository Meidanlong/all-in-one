package com.meidl.leetcode.string;


/**
 * 28 实现strStr()
 *
 *
 * 实现 strStr() 函数。
 *
 * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
 *
 * 示例 1:
 *
 * 输入: haystack = "hello", needle = "ll"
 * 输出: 2
 * 示例 2:
 *
 * 输入: haystack = "aaaaa", needle = "bba"
 * 输出: -1
 * 说明:
 *
 * 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
 *
 * 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。
 *
 * 注：while循环是先判断后循环 ，而do–while循环是先循环后判断
 */
public class StrStrSolution {

    public int strStr(String haystack, String needle) {
        //特殊情况
        if(null == needle || "".equals(needle))
            return 0;
        if(null == haystack || "".equals(haystack)||!haystack.contains(needle))
            return -1;

        //接下来肯定是haystack包含needle的情况
        char needleFirstChar = needle.charAt(0);//获取needle的第一位字符
        int returnInt = haystack.indexOf(needleFirstChar);//找到needle第一位字符在haystack中第一次出现的位置，初始化
        String partHaystack = haystack.substring(returnInt,returnInt+needle.length());//partHaystack是从returnInt开始截取needle长度的字符串

        while(!needle.equals(partHaystack)){//先判断partHaystack是否跟指定字符串相等
            returnInt++;
            returnInt += haystack.substring(returnInt).indexOf(needleFirstChar);//取从returnInt之后以为到末尾组成的新字符串中，第一次出现needleFirstChar的位置
            partHaystack = haystack.substring(returnInt,returnInt+needle.length());//从该位置截取needle长度的字符串用于作比较
        }

        return returnInt;
    }

    public int strStr1(String haystack, String needle) {
        //特殊情况
        if(null == needle || "".equals(needle))
            return 0;
        else
            return haystack.indexOf(needle);
    }



    public static void main(String[] args) {
        String haystack = "heloasdfawfelawfewllew";
        String needle = "ll";
        StrStrSolution strStrSolution = new StrStrSolution();
        System.out.println(strStrSolution.strStr(haystack,needle));
    }
}
