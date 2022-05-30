package com.meidl.leetcode.arrary;


/**
 * 122 买卖股票的最佳时机 II
 *
 *
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 示例 1:
 *
 * 输入: [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 *
 * 示例 2:
 *
 * 输入: [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
 *      因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 *
 * 示例 3:
 *
 * 输入: [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 */
public class MaxProfitSolution {

    public int maxProfit(int[] prices) {

        if(prices == null)
            return 0;

        int sum = 0;//利润
        int minindex = 0, maxindex = 0;

        for (int i = 1 ; i < prices.length; i++){
            if(prices[i-1] < prices[i]){
                maxindex  = i;
            }else if(prices[i-1] > prices[i]){
                sum += (prices[maxindex] - prices[minindex]);
                System.out.println(sum);
                minindex  = i;
            }
        }

        //处理未找到拐点，也就是一天比一天加个高的情况。
        //不知道为什么会报错：ArrayIndexOutOfBoundsException: 0,找不到prices[0]?这怎么可能?本地运行正常
        if(sum == 0 && (prices[maxindex] - prices[minindex]>0) ){
            sum = prices[maxindex] - prices[minindex];
        }

        return sum;

    }

    //官方思路：其实不一定要找到最大值和最小值，只要后一天比前一天是涨的，我们就把这部分差值加起来。这样加到最大值那一天，所加的数值是相等的。
    public int maxProfit2(int[] prices) {

        if (prices == null){
            return 0;
        }

        int sum = 0;
        for(int i = 1 ; i< prices.length ; i++){
            if(prices[i-1]<prices[i]){
                sum += (prices[i]-prices[i-1]);
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5};
        MaxProfitSolution maxProfitSolution = new MaxProfitSolution();
        System.out.println(maxProfitSolution.maxProfit(a));
    }
}
