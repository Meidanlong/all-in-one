package com.meidl.leetcode.string;

/**
 * 38 报数
 *
 * 报数序列是一个整数序列，按照其中的整数的顺序进行报数，得到下一个数。其前五项如下：
 *
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 1 被读作  "one 1"  ("一个一") , 即 11。
 * 11 被读作 "two 1s" ("两个一"）, 即 21。
 * 21 被读作 "one 2",  "one 1" （"一个二" ,  "一个一") , 即 1211。
 *
 * 给定一个正整数 n（1 ≤ n ≤ 30），输出报数序列的第 n 项。
 *
 * 注意：整数顺序将表示为一个字符串。
 *
 *
 *
 * 示例 1:
 *
 * 输入: 1
 * 输出: "1"
 * 示例 2:
 *
 * 输入: 4
 * 输出: "1211"
 */
public class CountAndSaySolution {

    public String countAndSay(int n) {

        StringBuilder initString = new StringBuilder("1");//初始化的字符串,注：已经初始化，代表第一步已经完成,之后遍历到n-1即可
        char[] initStringCharArray = null;
        int count = 1;


        for(int i=0; i<n-1; i++){
            initStringCharArray = initString.toString().toCharArray();
            StringBuilder resultString = new StringBuilder();//接受改变之后的结果的字符串
            for(int j=0; j<initStringCharArray.length; j++){//选择分情况讨论的方法
                if(j<initStringCharArray.length-1 && initStringCharArray[j] == initStringCharArray[j+1]){
                    count++;
                }else{
                    resultString.append(count).append(initStringCharArray[j]);
                    count = 1;//每次遍历完将count置为初始值
                }
            }
            initString = resultString;
        }

        return initString.toString();
    }

    public String countAndSay1(int n) {
        String a="1";
        for (int i = 1; i < n; i++) {
            a=changString(a);
        }
        return a;
    }

    private String changString(String a) {
        char[] chars=a.toCharArray();
        char cur=' ';

        StringBuilder builder=new StringBuilder();
        int num=0;
        for (int i = 0; i < chars.length; i++) {
            if(cur==chars[i]){
                num++;
            }else {
                if (num!=0){
                    builder.append(num);
                    builder.append(cur);
                }
                num=1;
                cur=chars[i];
            }
        }
        builder.append(num);
        builder.append(cur);
        return builder.toString();
    }

    public static void main(String[] args) {
        CountAndSaySolution countAndSaySolution = new CountAndSaySolution();
        System.out.println(countAndSaySolution.countAndSay1(6));
    }
}
