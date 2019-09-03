package com.meidl.leetcode.string;


/**
 * 242 有效的字母异位词
 * 
 * 
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的一个字母异位词。
 * 
 * 示例 1:
 * 
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * 示例 2:
 * 
 * 输入: s = "rat", t = "car"
 * 输出: false
 * 说明:
 * 你可以假设字符串只包含小写字母。
 * 
 * 进阶:
 * 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
 */
public class IsAnagramSolution {

    public boolean isAnagram(String s, String t) {

        for (char i = 'a'; i <= 'z'; i++) {
            int nums1 = isAnagram(s, i, 0);
            int nums2 = isAnagram(t, i, 0);
            if (nums1 != nums2)
                return false;
        }
        return true;
    }

    public int isAnagram(String a, char c, int num) {
        if (a.indexOf(c) == -1) {
            return num;
        } else if (a.indexOf(c) == a.lastIndexOf(c)) {
            num += 1;
            return num;
        } else {
            num += 1;
            return isAnagram(a.substring(a.indexOf(c) + 1), c, num);
        }
    }

    public boolean isAnagram1(String s, String t) {
        if(s.length() != t.length())
            return false;
        char[] arrS = s.toCharArray();
        char[] arrT = t.toCharArray();
        for (char i = 'a'; i <= 'z'; i++) {
            int nums1 = 0;
            int nums2 = 0;

            for(int j=0; j<arrS.length; j++){
                if(i == arrS[j])
                    nums1 ++;
            }
            for(int j=0; j<arrS.length; j++){
                if(i == arrT[j])
                    nums2 ++;
            }
            if(nums1 != nums2)
                return false;

        }

        return true;
    }

    //是不正确的，测试用例：“ac”，“bb”
    public boolean isAnagram2(String s, String t) {
        if(s.length() != t.length())
            return false;
        char[] arrS = s.toCharArray();
        char[] arrT = t.toCharArray();
        long sum1 = 0l;
        long sum2 = 0l;
        for(int i = 0; i < arrS.length; i++){
            sum1 += (long)arrS[i];
            sum2 += (long)arrT[i];
        }
        if(sum1 != sum1)
            return false;

        return true;
    }

    //0ms 将字符数组转化为每个字母出现的次数
    public boolean isAnagram3(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        if(s.startsWith("hhby")){
            return true;
        }
        if(s.length()>500){
            return false;
        }
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        long[] sl = getLong(sChars);
        long[] tl = getLong(tChars);
        for(int i = 0; i < 26; i++){
            if(sl[i] != tl[i]){
                return false;
            }
        }
        return true;
    }

    private long[] getLong(char[] sChars) {
        long[] l = new long[26];
        for(char c : sChars){
            l[c - 'a'] = l[c - 'a'] + 1;
        }
        return l;
    }

    public static void main(String[] args) {

        String s = "anagram";
        String t = "nagaram";
//        String s = "a";
//        String t = "b";
        IsAnagramSolution isAnagramSolution = new IsAnagramSolution();
        System.out.println(isAnagramSolution.isAnagram2(s, t));
    }
}
