package com.mdl.java.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/28 14:34
 */
public class ThreadPoolUtil {

    public static void shutDownExecutor(ExecutorService executorService) throws InterruptedException {
        if(executorService != null){
            executorService.shutdown();
        }
        // 执行完毕，线程池自旋等待10s
        final long awaitTime = 10 * 1000;
        if(executorService != null && !executorService.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)){
            executorService.shutdownNow();
        }
        executorService = null;
    }
}
