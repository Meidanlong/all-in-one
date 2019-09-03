package com.meidl.leetcode.string;


/**
 * 14 最长公共前缀
 *
 *
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 示例 1:
 *
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 *
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 * 说明:
 *
 * 所有输入只包含小写字母 a-z 。
 */
public class LongestCommonPrefixSolution {

    public String longestCommonPrefix(String[] strs) {
        //特殊情况
        if(null == strs||0 == strs.length)//字符数组为空的情况
            return "";
        if(1 == strs.length)//数组中只有一个字符串，那么前缀就是他本身
            return strs[0];

        boolean finalRound = false;
        boolean isPlusIndex = true;
        int index = 0;

        while(!finalRound){
            for(int i=1; i<strs.length; i++){
                if(index > strs[i-1].length() ||index > strs[i].length()){//如果超出某个字符串长度了，终止//这个也同时校验了某一个字符串为空的情况
                    finalRound = true;
                    isPlusIndex = false;
                    break;
                }
                if(strs[i-1].substring(0,index).equals(strs[i].substring(0,index))) {

                }  else{//如果前缀不相等了，终止
                    finalRound = true;
                    isPlusIndex = false;
                    break;
                }
            }

            if(isPlusIndex)
                index++;
        }

        return strs[0].substring(0,index-1);//因为我把最开始假设多了一位“”，所以实际的索引要减去一
    }

    public String longestCommonPrefix1(String[] strs) {
        //特殊情况
        if(null == strs||0 == strs.length)//字符数组为空的情况
            return "";
        if(1 == strs.length)//数组中只有一个字符串，那么前缀就是他本身
            return strs[0];

        boolean finalRound = false;
        boolean isPlusIndex = true;
        int index = 0;

        while(!finalRound){
            for(int i=1; i<strs.length; i++){
                if(index >= strs[i-1].length() ||index >= strs[i].length()){//如果超出某个字符串长度了，终止//这个也同时校验了某一个字符串为空的情况
                    finalRound = true;
                    isPlusIndex = false;
                    break;
                }
                if(strs[i-1].toCharArray()[index] == strs[i].toCharArray()[index]) {

                }  else{//如果前缀不相等了，终止
                    finalRound = true;
                    isPlusIndex = false;
                    break;
                }
            }

            if(isPlusIndex)
                index++;
        }

        return strs[0].substring(0,index);//因为我把最开始假设多了一位“”，所以实际的索引要减去一

    }


    public String longestCommonPrefix2(String[] strs) {
        if (strs.length == 0) {
            return "";
        } else if (strs.length == 1) {
            return strs[0];
        }

        int min = strs[0].length();
        String minStr = "";
        for (String str : strs) {
            if (str.length() <= min) {
                min = str.length();
                minStr = str;
            }
        }

        for (int size = min; size >= 1; size--) {
            for (int j = 0; j + size-1 <= min - 1; j++) {
                String sub = minStr.substring(j, j + size);
                boolean isFind = true;
                for (String s : strs) {
                    if (s.indexOf(String.valueOf(sub)) != 0) {
                        isFind = false;
                    }
                }
                if (isFind) {
                    return sub;
                }
            }
        }

        boolean isFind = true;
        for (char s : minStr.toCharArray()) {
            for (String str : strs) {
                if (str.indexOf(String.valueOf(s)) != 0) {
                    isFind = false;
                }
            }
            if (isFind) {
                return String.valueOf(s);
            }
        }
        return "";
    }

    public String longestCommonPrefix3(String[] strs) {
        if(strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 0; i <= strs.length - 1; i++){
            while(!strs[i].startsWith(prefix)){
                prefix = prefix.substring(0,prefix.length()-1);
            }
        }
        return prefix;
    }

    public String longestCommonPrefix4(String[] strs) {
        if(strs.length == 0 || strs[0].length() == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i <= strs.length - 1; i++){
            if(!"".equals(prefix)){
                while(!strs[i].startsWith(prefix)){
                    prefix = prefix.substring(0,prefix.length()-1);
                }
            }
        }
        return prefix;
    }

    public static void main(String[] args) {
        String[] a = {"flower","flow","flight"};
        LongestCommonPrefixSolution longestCommonPrefixSolution = new LongestCommonPrefixSolution();
        System.out.println(longestCommonPrefixSolution.longestCommonPrefix4(a));
    }
}
