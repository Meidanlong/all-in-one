package com.meidl.leetcode.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 22 括号生成
 *
 *
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 *
 * 例如，给出 n = 3，生成结果为：
 *
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 */
public class GenerateParenthesisSolution {
    List<String> strings = new ArrayList<String>();

    public List<String> generateParenthesis(int n) {

        if(n==0) return strings;

        if(n==1){
            strings.add("()");
            return strings;
        }

        generateParenthesis("",0,'(',n-1,n);

//        for (String s : strings){
//            System.out.println(s);
//        }
        return strings;
    }

    public void generateParenthesis(String string,int sum,char kuohao,int leftNum, int rightNum){



        string+=kuohao;
        if('('==kuohao) sum++;
        else sum--;

        if(sum >= 0){

            if(leftNum == 0 && rightNum == 0){
                strings.add(string.toString());
            }else{
                if(leftNum>0){
                    int ln = leftNum;
                    generateParenthesis(string,sum,'(',--ln,rightNum);
                }
                if(rightNum>0){
                    int rn = rightNum;
                    generateParenthesis(string,sum,')',leftNum,--rn);
                }
            }

        }

    }



    public List<String> generateParenthesis2(int n) {
        List<String> result = new ArrayList<>();
        char[] tmp = new char[2*n];
        f(result, tmp, 0, 0, 0, n);
        return result;
    }

    public void f(List<String> result, char[] tmp, int index, int left, int right, int n){
        if(left==n&&right==n){
            result.add(new String(tmp));
            return;
        }
        if(left+1<=n){
            tmp[index] = '(';
            f(result, tmp, index+1, left+1, right, n);
        }
        if(right+1<=left){
            tmp[index] = ')';
            f(result, tmp, index+1, left, right+1, n);
        }
    }

    public static void main(String[] args) {
        GenerateParenthesisSolution generateParenthesisSolution = new GenerateParenthesisSolution();
        generateParenthesisSolution.generateParenthesis(4);
    }
}
