package com.mdl.java.juc.future;

import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/5 19:54
 */
public abstract class abstractTask {

    abstract CompletableFuture<Integer> task1();

    abstract CompletableFuture<Integer> task2();

    abstract CompletableFuture<Integer> task3();

    abstract CompletableFuture<Integer> task4();

    abstract CompletableFuture<Integer> task5();


}
