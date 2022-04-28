package com.mdl.java.juc.threadpool;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/28 11:51
 */
class DiyThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        DiyThreadPoolTest.testThreadPoolIsolation();
    }

    /**
     * 测试线程池的线程隔离
     */
    public static void testThreadPoolIsolation() throws InterruptedException{
        ExecutorService executorService = DiyThreadPool.newThreadPool(8, 10, new SynchronousQueue<>());
//        ExecutorService executorService = DiyThreadPool.newLinkedBlockingQueueThreadPool(8, 20);
        for(int i=0; i<100; i++){
            RunnableThread runnableThread = new RunnableThread(i);
            executorService.submit(runnableThread);
        }
        ThreadPoolUtil.shutDownExecutor(executorService);
//        Thread.sleep(10 * 1000);
    }

    static class RunnableThread implements Runnable{
        int sleepTime;
        public RunnableThread(int sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            int i=0;
            while (i < 10){
                System.out.println(Thread.currentThread().getName() + " -> " + ++i);
                try {
                    Thread.sleep(10 * sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}