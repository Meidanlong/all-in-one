package com.meidl.leetcode.string;

/**
 * 反转字符串
 *
 *
 * 编写一个函数，其作用是将输入的字符串反转过来。
 *
 * 示例 1:
 *
 * 输入: "hello"
 * 输出: "olleh"
 * 示例 2:
 *
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: "amanaP :lanac a ,nalp a ,nam A"
 */
public class ReverseStringSolution {

    public String reverseString(String s) {
        char[] arr = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for(int i=arr.length-1; i>=0; i--){
            sb.append(arr[i]);
        }

        return sb.toString();
    }

    public String reverseString1(String s) {
        char[] arr = s.toCharArray();
        int mid = arr.length/2;
        char temp;

        for(int i=0; i<mid; i++){
            temp = arr[i];
            arr[i] = arr[arr.length-1-i];
            arr[arr.length-1-i] = temp;
        }

        return new String(arr);
    }

    public String reverseString2(String s) {
        if("".equals(s))
            return "";
        char[] arr = s.toCharArray();
        if(arr.length ==1)
            return new String(arr);
        int mid = arr.length/2;
        int index = 0;
        char temp;

        while(index < mid){
            temp = arr[index];
            arr[index] = arr[arr.length-1-index];
            arr[arr.length-1-index] = temp;
            index++;
        }

        return new String(arr);
    }

    public String reverseString3(String s) {
                int i = 0;
                int j = s.length()-1;
                char[] ch = s.toCharArray();
                while(i<j){
                    swap(ch,i,j);
                    i++;
                    j--;
        }
        return new String(ch);
    }
    public void swap(char[] ch,int i,int j){
        char temp = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
    }

    public static void main(String[] args) {
        String s = "A man, a plan, a canal: Panama";
        ReverseStringSolution reverseStringSolution = new ReverseStringSolution();
        System.out.println(reverseStringSolution.reverseString2(s));
    }
}
