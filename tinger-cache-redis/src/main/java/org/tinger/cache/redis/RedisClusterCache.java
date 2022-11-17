package org.tinger.cache.redis;

import io.lettuce.core.GetExArgs;
import io.lettuce.core.SetArgs;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import org.tinger.core.cache.Cache;
import org.tinger.core.cache.CacheDriver;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisClusterCache implements Cache {
    private final StatefulRedisClusterConnection<String, Object> connection;

    public RedisClusterCache(RedisClusterClient client) {
        connection = client.connect(new TingerRedisCodec());
    }

    @Override
    public CacheDriver driver() {
        return CacheDriver.RDS;
    }

    @Override
    public Object get(String key) {
        return this.connection.sync().get(key);
    }

    @Override
    public Object get(String key, int expiry) {
        return this.connection.sync().getex(key, new GetExArgs().ex(expiry));
    }

    @Override
    public Object get(String key, Date expiry) {
        return this.connection.sync().getex(key, new GetExArgs().exAt(expiry));
    }

    @Override
    public Map<String, Object> get(Collection<String> keys) {
        return keys.stream().collect(Collectors.toMap(x -> x, this::get));
    }

    @Override
    public Map<String, Object> get(Collection<String> keys, int expiry) {
        return keys.stream().collect(Collectors.toMap(x -> x, x -> get(x, expiry)));
    }

    @Override
    public Map<String, Object> get(Collection<String> keys, Date expiry) {
        return keys.stream().collect(Collectors.toMap(x -> x, x -> get(x, expiry)));
    }

    @Override
    public void set(String key, Object value) {
        this.connection.sync().set(key, value);
    }

    @Override
    public void set(String key, Object value, int expiry) {
        this.connection.sync().set(key, value, new SetArgs().ex(expiry));
    }

    @Override
    public void set(String key, Object value, Date expiry) {
        this.connection.sync().set(key, value, new SetArgs().exAt(expiry));
    }

    @Override
    public void set(Map<String, Object> kv) {
        for (Map.Entry<String, Object> entry : kv.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void set(Map<String, Object> kv, int expiry) {
        for (Map.Entry<String, Object> entry : kv.entrySet()) {
            set(entry.getKey(), entry.getValue(), expiry);
        }
    }

    @Override
    public void set(Map<String, Object> kv, Date expiry) {
        for (Map.Entry<String, Object> entry : kv.entrySet()) {
            set(entry.getKey(), entry.getValue(), expiry);
        }
    }

    @Override
    public void exp(String key, int expiry) {
        this.connection.sync().expire(key, expiry);
    }

    @Override
    public void exp(String key, Date expiry) {
        this.connection.sync().expireat(key, expiry);
    }

    @Override
    public void exp(Collection<String> keys, int expiry) {
        for (String key : keys) {
            exp(key, expiry);
        }
    }

    @Override
    public void exp(Collection<String> keys, Date expiry) {
        for (String key : keys) {
            exp(key, expiry);
        }
    }

    @Override
    public void del(String key) {
        this.connection.sync().del(key);
    }

    @Override
    public void del(Collection<String> keys) {
        this.connection.sync().del(keys.toArray(new String[0]));
    }
}
