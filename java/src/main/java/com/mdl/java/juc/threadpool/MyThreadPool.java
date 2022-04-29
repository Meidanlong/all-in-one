package com.mdl.java.juc.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:自定义线程池
 * @author: meidanlong
 * @date: 2022/4/28 11:26
 */
public class MyThreadPool {

    public static ExecutorService newArrayBlockingQueueThreadPool(int coreSize, int maxSize, int capacity){
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(capacity);
        return newThreadPool(coreSize, maxSize, workQueue);
    }

    public static ExecutorService newLinkedBlockingQueueThreadPool(int coreSize, int maxSize){
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        return newThreadPool(coreSize, maxSize, workQueue);
    }

    public static ExecutorService newThreadPool(int coreSize, int maxSize, BlockingQueue<Runnable> workQueue){
        return new ThreadPoolExecutor(coreSize, maxSize, 0, TimeUnit.SECONDS, workQueue);
    }

}
