package com.mdl.java.base;

/**
 * @description:TryCache测试
 *
 * try和catch中return的值会暂存为本地变量，如果finally中return有值，则会覆盖该本地变量，直接返回
 *
 * @author: meidanlong
 * @date: 2022/11/24 10:07
 */
public class TryCacheTest {

    public static int test(){
        try{
            System.out.println("try执行逻辑");
            int a = 1/0; // 异常代码
            return 1;
        }catch (Exception e){
            System.out.println("catch执行逻辑");
//            return 2;
        }finally {
            System.out.println("finally执行逻辑");
            return 3;
        }
//        return 4;
    }

    public static void main(String[] args) {
        System.out.println(TryCacheTest.test());
    }
}
