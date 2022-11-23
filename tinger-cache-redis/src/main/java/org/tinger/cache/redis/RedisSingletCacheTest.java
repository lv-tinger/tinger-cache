package org.tinger.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

public class RedisSingletCacheTest {
    public static void main(String[] args) {
        RedisURI redisURI = RedisURI.builder().withHost("localhost").withPort(6379).build();
        RedisClient redisClient = RedisClient.create(redisURI);
        RedisSingletCache cache = new RedisSingletCache(redisClient);
        cache.set("tinger", 1234567);
        System.out.println(cache.get("tinger"));
    }
}
