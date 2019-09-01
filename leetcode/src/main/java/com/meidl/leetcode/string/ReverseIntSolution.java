package com.meidl.leetcode.string;

/**
 * 整数反转
 *
 *
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * 示例 1:
 *
 * 输入: 123
 * 输出: 321
 *  示例 2:
 *
 * 输入: -123
 * 输出: -321
 * 示例 3:
 *
 * 输入: 120
 * 输出: 21
 * 注意:
 *
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−2^31,  2^31 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 */
public class ReverseIntSolution {

    public int reverse(int x) {
        char[] arr = String.valueOf(x).toCharArray();
        boolean isFu = false;
        int head = 0;
        int tail = arr.length-1;
        if(head < tail){
            if('-' == arr[head]){
                head++;
                isFu = true;
            }
            if('0' == arr[tail])
                tail--;

            int result = 0;
            try{
                if(isFu)
                    result = 0-Integer.valueOf(reverseString3(arr,head,tail));
                else
                    result=Integer.valueOf(reverseString3(arr,head,tail));
            }finally {
                return result;
            }

        }else
            return x;
    }

    public String reverseString3(char[] arr,int head,int tail) {
        int headIndex = head;
        int tailIndex = tail;
        while(headIndex<tailIndex){
            swap(arr,headIndex,tailIndex);
            headIndex++;
            tailIndex--;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=head; i<=tail; i++){
            sb.append(arr[i]);
        }
        return sb.toString();
    }
    public void swap(char[] ch,int i,int j){
        char temp = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
    }

    public int reverse1(int x) {
        int weishu = 0;
        long newx = x;
        long result = 0l;
        try{
            if (x>0){
                if(x > 2147483647)
                    return 0;
                int t=x/10;
                while (t > 0) {
                    weishu++;
                    t /= 10;
                }
                if(weishu > 0 && x%10!=0){
                    for(int i=0; i<=weishu ; i++){
                        int mi1 = mi(weishu-i);
                        int mi2 = mi(i);
                        result += (long)((x/mi1)%10)*mi2;
                        if(result > 2147483647)
                            throw new Exception();
                    }
                    return (int)result;
                }else if(weishu > 0 && x%10==0){
                    for(int i=0; i<=weishu-1 ; i++){
                        int mi1 = mi(weishu-i);
                        int mi2 = mi(i);
                        result += ((x/mi1)%10)*mi2;
                        if(result > 2147483647)
                            throw new Exception();
                    }
                    return (int)result;
                }else{
                    return x;
                }
            }else if(x<0){
                if(x <= -2147483648)
                    return 0;
                x = -x;
                int t=x/10;
                while (t > 0) {
                    weishu++;
                    t /= 10;
                }
                if(weishu > 0 && x%10!=0){
                    for(int i=0; i<=weishu ; i++){
                        int mi1 = mi(weishu-i);
                        int mi2 = mi(i);
                        result += (long)((x/mi1)%10)*mi2;
                        if(result > 2147483648l) {
                            throw new Exception();
                        }
                    }
                    return (int)(0-result);
                }else if(weishu > 0 && x%10==0){
                    for(int i=0; i<=weishu-1 ; i++){
                        int mi1 = mi(weishu-i);
                        int mi2 = mi(i);
                        result += (long)((x/mi1)%10)*mi2;
                        if( result > 2147483648l)
                            throw new Exception();
                    }
                    return (int)(0-result);
                }else{
                    return x;
                }
            }else
                return 0;
        }catch (Exception e){
            return 0;
        }
    }
    public int mi(int mi){
        int[] mis = new int[mi+1];
        mis[0] = 1;
        StringBuilder sb = new StringBuilder();
        for(int i:mis)
            sb.append(i);
        int i =  Integer.valueOf(sb.toString());
        return i;
    }

    public static void main(String[] args) {
        ReverseIntSolution reverseIntSolution = new ReverseIntSolution();
        System.out.println(reverseIntSolution.reverse1(-2147483640));
    }
}
