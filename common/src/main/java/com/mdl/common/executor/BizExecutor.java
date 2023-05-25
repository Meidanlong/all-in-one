package com.mdl.common.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 业务公用线程池
 *
 * @author meidanlong
 * @date 2023/04/11
 */
public class BizExecutor {

    private static final BizExecutor INSTANCE = new BizExecutor();

    public static BizExecutor getInstance() {
        return INSTANCE;
    }

    private ExecutorService executorService = null;

    private BizExecutor() {
        init();
    }

    private void init() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("biz-pool-%d").build();
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        executorService = new ThreadPoolExecutor(poolSize, poolSize, 1, TimeUnit.MINUTES,
                new SynchronousQueue<Runnable>(), namedThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public ExecutorService getThreadPool() {
        return executorService;
    }
}
