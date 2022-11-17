package org.tinger.cache.redis;

import io.lettuce.core.GetExArgs;
import io.lettuce.core.RedisClient;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.tinger.core.cache.Cache;
import org.tinger.core.cache.CacheDriver;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tinger on 2022-11-17
 */
public class RedisSingletCache implements Cache {
    private final StatefulRedisConnection<String, Object> connection;

    public RedisSingletCache(RedisClient client) {
        connection = client.connect(new TingerRedisCodec());
    }

    @Override
    public CacheDriver driver() {
        return CacheDriver.RDS;
    }

    @Override
    public Object get(String key) {
        return connection.sync().get(key);
    }

    @Override
    public Object get(String key, int expiry) {
        return connection.sync().getex(key, new GetExArgs().ex(expiry));
    }

    @Override
    public Object get(String key, Date expiry) {
        return connection.sync().getex(key, new GetExArgs().exAt(expiry));
    }

    @Override
    public Map<String, Object> get(Collection<String> keys) {
        RedisCommands<String, Object> sync = connection.sync();
        return keys.stream().collect(Collectors.toMap(x -> x, sync::get));
    }

    @Override
    public Map<String, Object> get(Collection<String> keys, int expiry) {
        RedisCommands<String, Object> sync = connection.sync();
        GetExArgs ex = new GetExArgs().ex(expiry);
        return keys.stream().collect(Collectors.toMap(x -> x, x -> sync.getex(x, ex)));
    }

    @Override
    public Map<String, Object> get(Collection<String> keys, Date expiry) {
        RedisCommands<String, Object> sync = connection.sync();
        GetExArgs ex = new GetExArgs().exAt(expiry);
        return keys.stream().collect(Collectors.toMap(x -> x, x -> sync.getex(x, ex)));
    }

    @Override
    public void set(String key, Object value) {
        connection.sync().set(key, value);
    }

    @Override
    public void set(String key, Object value, int expiry) {
        connection.sync().set(key, value, new SetArgs().ex(expiry));
    }

    @Override
    public void set(String key, Object value, Date expiry) {
        connection.sync().set(key, value, new SetArgs().exAt(expiry));
    }

    @Override
    public void set(Map<String, Object> kv) {
        RedisCommands<String, Object> sync = connection.sync();
        kv.forEach(sync::set);
    }

    @Override
    public void set(Map<String, Object> kv, int expiry) {
        RedisCommands<String, Object> sync = connection.sync();
        SetArgs ex = new SetArgs().ex(expiry);
        kv.forEach((key, value) -> sync.set(key, value, ex));
    }

    @Override
    public void set(Map<String, Object> kv, Date expiry) {
        RedisCommands<String, Object> sync = connection.sync();
        SetArgs ex = new SetArgs().exAt(expiry);
        kv.forEach((key, value) -> sync.set(key, value, ex));
    }

    @Override
    public void exp(String key, int expiry) {
        RedisCommands<String, Object> sync = connection.sync();
        sync.expire(key, expiry);
    }

    @Override
    public void exp(String key, Date expiry) {
        RedisCommands<String, Object> sync = connection.sync();
        sync.expireat(key, expiry);
    }

    @Override
    public void exp(Collection<String> keys, int expiry) {
        RedisCommands<String, Object> sync = connection.sync();
        keys.forEach(x -> sync.expire(x, expiry));
    }

    @Override
    public void exp(Collection<String> keys, Date expiry) {
        RedisCommands<String, Object> sync = connection.sync();
        keys.forEach(x -> sync.expireat(x, expiry));
    }

    @Override
    public void del(String key) {
        RedisCommands<String, Object> sync = connection.sync();
        sync.del(key);
    }

    @Override
    public void del(Collection<String> keys) {
        RedisCommands<String, Object> sync = connection.sync();
        sync.del(keys.toArray(new String[0]));
    }
}
