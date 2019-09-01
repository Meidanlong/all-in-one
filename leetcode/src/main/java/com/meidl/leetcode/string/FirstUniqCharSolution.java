package com.meidl.leetcode.string;


import java.util.HashMap;

/**
 * 字符串中的第一个唯一字符
 *
 *
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 *
 * 案例:
 *
 * s = "leetcode"
 * 返回 0.
 *
 * s = "loveleetcode",
 * 返回 2.
 *
 *
 * 注意事项：您可以假定该字符串只包含小写字母。
 */
public class FirstUniqCharSolution {

    public int firstUniqChar(String s) {

        if(s.length() == 0)
            return -1;
        if(s.length() == 1)
            return 0;

        char[] chars = s.toCharArray();
        int[] ints = new int[chars.length];
        HashMap<Character,Integer> hashMap = new HashMap<>();
        for(int i=0; i<chars.length; i++){
            if(hashMap.containsKey(chars[i])){
                ints[hashMap.get(chars[i])] = -1;
                ints[i] = -1;
            }else{
                hashMap.put(chars[i],i);
            }
        }
        for(int i=0; i<ints.length; i++){
            if(ints[i] == 0)
                return i;
        }
        return -1;
    }

    public int firstUniqChar1(String s) {
        if(s.length() == 0)
            return -1;
        if(s.length() == 1)
            return 0;
        int res = -1;
        for(char i ='a';i<='z';i++){
            int index = s.indexOf(i);
            if(index!=-1 && index == s.lastIndexOf(i)){
                res = (res==-1 || res > index) ? index : res;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "cc";
        FirstUniqCharSolution firstUniqCharSolution = new FirstUniqCharSolution();
        System.out.println(firstUniqCharSolution.firstUniqChar1(s));
    }
}
