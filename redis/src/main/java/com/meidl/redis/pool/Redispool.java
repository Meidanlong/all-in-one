package com.meidl.redis.pool;

import redis.clients.jedis.JedisPool;

public class Redispool {
    private static JedisPool pool;//jedis连接池
    private static Integer maxTotal;//最大连接数
    private static Integer maxIdle;//在jedispool中最大的idle状态（空闲的）jedis实例的个数
    private static Integer minIdle;//在jedispool中最小的idle状态（空闲的）jedis实例的个数
    private static Boolean testOnBorrow ;//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值为true,则得到的jedis实例肯定是可以使用的
    private static Boolean testOnReturn ;//在return一个jedis实例的时候，是否要进行验证操作，如果赋值为true,则放回的jedispool的实例肯定是可以使用的
}
