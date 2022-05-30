package com.meidl.leetcode.string;


/**
 * 8 字符串转换整数 (atoi)
 *
 *
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 *
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 *
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 *
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 *
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 *
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 *
 * 说明：
 *
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−2^31,  2^31 − 1]。如果数值超过这个范围，qing返回  INT_MAX (2^31 − 1) 或 INT_MIN (−2^31) 。
 *
 * 示例 1:
 *
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 *
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 *      我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 *
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 *
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 *      因此无法执行有效的转换。
 * 示例 5:
 *
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 *      因此返回 INT_MIN (−2^31) 。
 */
public class MyAtoiSolution {

    public int myAtoi(String str) {

        if(null == str || "".equals(str.trim()) || !('-' == str.trim().charAt(0)||'+' == str.trim().charAt(0)|| ('0'<=str.trim().charAt(0) && '9'>=str.trim().charAt(0))))
            return 0;

        StringBuilder sb = new StringBuilder();
        int index = 0;
        for(char i:str.trim().toCharArray()){
            if(index > 0 && '-' == i)
                return 0;
            if(!('-' == i||'+' == i|| ('0'<=i && '9'>=i))){
                break;
            }else{
                if('+' != i&&!(index == 0&&'0' == i)){
                    sb.append(i);
                    index++;
                }
            }
        }

        if("-".equals(sb.toString())||"".equals(sb.toString()))
            return 0;

        if('-' == sb.toString().charAt(0) && sb.toString().length()>String.valueOf(Integer.MIN_VALUE).length())
            return Integer.MIN_VALUE;
        else if('-' != sb.toString().charAt(0) && sb.toString().length()>String.valueOf(Integer.MAX_VALUE).length())
            return Integer.MAX_VALUE;


        long result = Long.valueOf(sb.toString());
        if(result > Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else if(result < Integer.MIN_VALUE){
            return Integer.MIN_VALUE;
        }else{
            return (int)result;
        }

    }

    public int myAtoi1(String str) {
        try{
            String afterTrim = str.trim();
            char first = afterTrim.charAt(0);
            if('-' == first||'+' == first){
                afterTrim = afterTrim.substring(1);
            }else if('0'<=first && first<='9'){
                first = '+';
            }else{
                return 0;
            }
            if(afterTrim.length()==0)
                throw new Exception();


            boolean isFirstChar = true;
            StringBuilder sb = new StringBuilder();
            if('-' == first)
                sb.append('-');
            for(char i:afterTrim.toCharArray()){
                if(isFirstChar && '0'==i){
                    continue;
                }else if(isFirstChar && !('1'<=i && '9'>=i)){
                    return 0;
                }else if(!isFirstChar && !('0'<=i && '9'>=i)){
                    break;
                }else{
                    sb.append(i);
                    isFirstChar = false;
                }
            }

            if('-' == sb.toString().charAt(0) && sb.toString().length()>String.valueOf(Integer.MIN_VALUE).length())
                return Integer.MIN_VALUE;
            else if('-' != sb.toString().charAt(0) && sb.toString().length()>String.valueOf(Integer.MAX_VALUE).length())
                return Integer.MAX_VALUE;


            long result = Long.valueOf(sb.toString());
            if(result > Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }else if(result < Integer.MIN_VALUE){
                return Integer.MIN_VALUE;
            }else{
                return (int)result;
            }


        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        MyAtoiSolution myAtoiSolution = new MyAtoiSolution();
        System.out.println(myAtoiSolution.myAtoi1("+-2"));
    }
}
