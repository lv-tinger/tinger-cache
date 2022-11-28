package org.tinger.cache.core;

import org.tinger.core.cache.CacheDriver;

public abstract class AbstractCacheBuilder implements CacheBuilder{
    private final CacheDriver cacheDriver;

    public CacheDriver driver() {
        return this.cacheDriver;
    }

    protected AbstractCacheBuilder(CacheDriver cacheDriver) {
        this.cacheDriver = cacheDriver;
    }
}
