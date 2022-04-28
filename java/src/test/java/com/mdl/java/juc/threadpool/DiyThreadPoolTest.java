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

    /**
     * 测试:
     * 1. 线程池的线程在阻塞等待时，会不会支援消费别的线程
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = DiyThreadPool.newThreadPool(8, 10, new SynchronousQueue<>());
//        ExecutorService executorService = DiyThreadPool.newLinkedBlockingQueueThreadPool(8, 20);
        for(int i=0; i<100; i++){
            RunnableThread runnableThread = new RunnableThread(i);
            executorService.submit(runnableThread);
            if(100 % 3 == 0){
                runnableThread.notifyAll();
            }
        }
        ThreadPoolUtil.shutDownExecutor(executorService);
        Thread.sleep(10 * 1000);
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
                    this.wait(10 * sleepTime);
//                    this.notifyAll();
//                    System.out.println(Thread.currentThread().getName() + " state: " + Thread.currentThread().getState());
//                    Thread.sleep(10 * sleepTime);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}