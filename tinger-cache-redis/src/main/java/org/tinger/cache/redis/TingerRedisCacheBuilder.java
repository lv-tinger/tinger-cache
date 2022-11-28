package org.tinger.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import org.tinger.cache.core.AbstractCacheBuilder;
import org.tinger.common.utils.StringUtils;
import org.tinger.core.cache.Cache;
import org.tinger.core.cache.CacheDriver;

import java.util.List;
import java.util.stream.Collectors;

public class TingerRedisCacheBuilder extends AbstractCacheBuilder {


    private static final String singlet = "singlet";
    private static final String cluster = "cluster";
    private static final String sentinel = "sentinel";

    protected TingerRedisCacheBuilder() {
        super(CacheDriver.RDS);
    }

    @Override
    public Cache build(Object config) {
        RedisConfig cfg = (RedisConfig) config;

        switch (cfg.getType()) {
            case singlet:
                RedisURI redisURI = buildURI(cfg.getItems().get(0));
                RedisClient client = RedisClient.create(redisURI);
                return new RedisSingletCache(client);
            case cluster:
                List<RedisURI> redisURIS = cfg.getItems().stream().map(this::buildURI).collect(Collectors.toList());
                RedisClusterClient clusterClient = RedisClusterClient.create(redisURIS);
                return new RedisClusterCache(clusterClient);
            default:
                return null;
        }
    }

    private RedisURI buildURI(RedisHostConfig config) {
        return RedisURI.builder().withHost(config.getHost()).withPort(config.getPort()).build();
    }

    @Override
    public Class<?> configType() {
        return RedisConfig.class;
    }
}
