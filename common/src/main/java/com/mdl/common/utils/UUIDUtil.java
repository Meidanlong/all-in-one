package com.mdl.common.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/7/11 8:23 PM
 */
public class UUIDUtil {
    private static boolean IS_THREADLOCALRANDOM_AVAILABLE = false;
    private static Random random;
    private static final long   leastSigBits;
    private static final ReentrantLock lock = new ReentrantLock();
    private static long lastTime;

    static {
        try {
            IS_THREADLOCALRANDOM_AVAILABLE = null != UUIDUtil.class.getClassLoader().loadClass(
                    "java.util.concurrent.ThreadLocalRandom"
            );
        } catch(ClassNotFoundException e) {
        }

        byte[] seed = new SecureRandom().generateSeed(8);
        leastSigBits = new BigInteger(seed).longValue();
        if(!IS_THREADLOCALRANDOM_AVAILABLE) {
            random = new Random(leastSigBits);
        }
    }

    private UUIDUtil() {
    }

    /**
     * 随机UUID
     * @return
     */
    public static String random(){
        return randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Create a new random UUID.
     *
     * @return the new UUID
     */
    private static UUID randomUUID() {
        byte[] randomBytes = new byte[16];
        if(IS_THREADLOCALRANDOM_AVAILABLE) {
            java.util.concurrent.ThreadLocalRandom.current().nextBytes(randomBytes);
        } else {
            random.nextBytes(randomBytes);
        }

        long mostSigBits = 0;
        for(int i = 0; i < 8; i++) {
            mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
        }
        long leastSigBits = 0;
        for(int i = 8; i < 16; i++) {
            leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
        }

        return new UUID(mostSigBits, leastSigBits);
    }

    /**
     * 基于时间的UUID
     * @return
     */
    public static String timeBased(){
        return createUUID().toString().replaceAll("-", "");
    }

    /**
     * Create a new time-based UUID.
     *
     * @return the new UUID
     */
    private static UUID createUUID(){
        long timeMillis;

        try {
            timeMillis = (DateTimeUtil.curTime().getTime() * 10000) + 0x01B21DD213814000L;
        } catch (IOException e) {
            timeMillis = (System.currentTimeMillis() * 10000) + 0x01B21DD213814000L;
        }

        lock.lock();
        try {
            if(timeMillis > lastTime) {
                lastTime = timeMillis;
            } else {
                timeMillis = ++lastTime;
            }
        } finally {
            lock.unlock();
        }

        // time low
        long mostSigBits = timeMillis << 32;

        // time mid
        mostSigBits |= (timeMillis & 0xFFFF00000000L) >> 16;

        // time hi and version
        mostSigBits |= 0x1000 | ((timeMillis >> 48) & 0x0FFF);

        return new UUID(mostSigBits, leastSigBits);
    }

}
