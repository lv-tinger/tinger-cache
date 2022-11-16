package org.tinger.cache.redis;

import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import org.tinger.core.cache.Cache;
import org.tinger.core.cache.CacheDriver;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class RedisCache implements Cache {

    @Override
    public CacheDriver driver() {
        return CacheDriver.RDS;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public Object get(String key, int expiry) {
        return null;
    }

    @Override
    public Object get(String key, Date expiry) {
        return null;
    }

    @Override
    public Map<String, Object> get(Collection<String> keys) {
        return null;
    }

    @Override
    public Map<String, Object> get(Collection<String> keys, int expiry) {
        return null;
    }

    @Override
    public Map<String, Object> get(Collection<String> keys, Date expiry) {
        return null;
    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public void set(String key, Object value, int expiry) {

    }

    @Override
    public void set(String key, Object value, Date expiry) {

    }

    @Override
    public void set(Map<String, Object> kv) {

    }

    @Override
    public void set(Map<String, Object> kv, int expiry) {

    }

    @Override
    public void set(Map<String, Object> kv, Date expiry) {

    }

    @Override
    public void exp(String key, int expiry) {

    }

    @Override
    public void exp(String key, Date expiry) {

    }

    @Override
    public void exp(Collection<String> keys, int expiry) {

    }

    @Override
    public void exp(Collection<String> keys, Date expiry) {

    }

    @Override
    public void del(String key) {

    }

    @Override
    public void del(Collection<String> keys) {

    }
}
