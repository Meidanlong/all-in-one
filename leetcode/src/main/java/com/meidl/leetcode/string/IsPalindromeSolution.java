package com.meidl.leetcode.string;


/**
 * 验证回文字符串
 *
 *
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 *
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * 示例 1:
 *
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * 示例 2:
 *
 * 输入: "race a car"
 * 输出: false
 */
public class IsPalindromeSolution {

    public boolean isPalindrome(String s) {
        if(null==s||s.length()==1)
            return true;
        int head = 0;
        int tail = s.length()-1;
        char[] arr = s.toCharArray();
        while (head<tail){
            while(head<=tail){
                if((48<=arr[head]&&arr[head]<=57)||(65<=arr[head]&&arr[head]<=90)||(97<=arr[head]&&arr[head]<=122)){
                    break;
                }else{
                    head++;
                }
            }
            while(head<=tail){
                if((48<=arr[tail]&&arr[tail]<=57)||(65<=arr[tail]&&arr[tail]<=90)||(97<=arr[tail]&&arr[tail]<=122)){
                    break;
                }else{
                    tail--;
                }
            }

            if(head>tail){
                return true;
            }else if((48<=arr[head]&&arr[head]<=57)&&(48<=arr[tail]&&arr[tail]<=57)){
                if(arr[head]==arr[tail]){
                    head++;
                    tail--;
                }else{
                    return false;
                }
            }else if(((65<=arr[head]&&arr[head]<=90)||(97<=arr[head]&&arr[head]<=122))&&((65<=arr[tail]&&arr[tail]<=90)||(97<=arr[tail]&&arr[tail]<=122))){
                if(arr[head]==arr[tail]||arr[head]==arr[tail]+32||arr[head]+32==arr[tail]){
                    head++;
                    tail--;
                }else
                    return false;
            }else{
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("a: "+(int)'a');
        System.out.println("A: "+(int)'A');
        System.out.println("z: "+(int)'z');
        System.out.println("Z: "+(int)'Z');
        System.out.println("1: "+(int)'1');
        System.out.println("0: "+(int)'0');
        System.out.println("9: "+(int)'9');

        IsPalindromeSolution isPalindromeSolution = new IsPalindromeSolution();
        System.out.println(isPalindromeSolution.isPalindrome(",."));
    }
}
