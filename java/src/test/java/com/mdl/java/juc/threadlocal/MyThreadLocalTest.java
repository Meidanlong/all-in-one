package com.mdl.java.juc.threadlocal;


import com.mdl.common.domain.demo.Dog;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/4/29 11:07
 */
class MyThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        testStaticInheritableThreadLocal();
//        testInheritableThreadLocal();
    }

    /**
     * 测试静态的可继承的threadLocal
     *
     * 预期：
     * main -> 球球
     * Thread-0 -> 球球
     * Thread-0 -> 圆圆
     * main -> 圆圆
     */
    private static void testStaticInheritableThreadLocal() throws InterruptedException {
        Dog dog = new Dog();
        dog.setName("球球");
        MyStaticThreadLocal.setMyITL(dog);
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getId() + " -> " + MyStaticThreadLocal.getMyITL().getName());
        new Thread(new StaticTLSub()).start();
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getId()+ " -> " + MyStaticThreadLocal.getMyITL().getName());
    }

    /**
     * 测试不是静态的可继承的threadLocal
     *
     * 预期：
     * main -> 球球
     * Thread-0 -> 球球
     * Thread-0 -> 圆圆
     * main -> 球球
     * @throws InterruptedException
     */
    private static void testInheritableThreadLocal() throws InterruptedException {
        MyThreadLocal myThreadLocal = new MyThreadLocal();
        Dog dog = new Dog();
        dog.setName("球球");
        myThreadLocal.setMyITL(dog);
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getId()+ " -> " + myThreadLocal.getMyITL().getName());
        new Thread(new StaticTLSub()).start();
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getId()+ " -> " + myThreadLocal.getMyITL().getName());
    }


    static class StaticTLSub implements Runnable{

        @Override
        public void run() {
//            notChangeObj();
            changeObj();
        }

        private void notChangeObj(){
            System.out.println(Thread.currentThread().getName() + " -> " + MyStaticThreadLocal.getMyITL().getName());
            MyStaticThreadLocal.getMyITL().setName("圆圆");
            System.out.println(Thread.currentThread().getName() + " -> " + MyStaticThreadLocal.getMyITL().getName());
        }

        private void changeObj(){
            System.out.println(Thread.currentThread().getName() + " -> " + MyStaticThreadLocal.getMyITL().getName());
            Dog subDog = new Dog();
            subDog.setName("圆圆");
            MyStaticThreadLocal.setMyITL(subDog);
            System.out.println(Thread.currentThread().getName() + " -> " + MyStaticThreadLocal.getMyITL().getName());
        }
    }

    static class TLSub implements Runnable{

        @Override
        public void run() {
            MyThreadLocal myThreadLocal = new MyThreadLocal();
            System.out.println(Thread.currentThread().getName() + " -> " + myThreadLocal.getMyITL().getName());
            myThreadLocal.getMyITL().setName("圆圆");
            System.out.println(Thread.currentThread().getName() + " -> " + myThreadLocal.getMyITL().getName());
        }
    }

}