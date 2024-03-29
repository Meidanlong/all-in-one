package com.mdl.java.juc.future;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/5 13:45
 */
public class CompletableFutureUtil {

    class thread1 implements Runnable{

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class thread2 implements Runnable{

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int number = new Random().nextInt(3) + 1;
                try {
                    TimeUnit.SECONDS.sleep(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第一阶段：" + number);
                return number;
            }
        });

        CompletableFuture<Long> future2 = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                int number = new Random().nextInt(3) + 1;
                try {
                    TimeUnit.SECONDS.sleep(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第二阶段：" + number);
                return Long.valueOf(number);
            }
        });

        CompletableFuture<Integer> result = future1
                .thenCombineAsync(future2, (x, y) -> {
                    System.out.println("CombineAsync：" + 1);
                    return Integer.valueOf((int) (x + y));
                }).thenCombineAsync(future2, new BiFunction<Integer, Long, Integer>() {
                    @Override
                    public Integer apply(Integer x, Long y) {
                        System.out.println("CombineAsync：" + 2);
                        return Integer.valueOf((int) (x + y));
                    }
                });
        System.out.println("组合后结果：" + result.get());
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTime() + "ms");
    }
}
