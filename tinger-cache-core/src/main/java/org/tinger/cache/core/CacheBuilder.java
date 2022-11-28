package org.tinger.cache.core;

import org.tinger.core.cache.Cache;
import org.tinger.core.cache.CacheDriver;

public interface CacheBuilder {
    CacheDriver driver();
    Cache build(Object config);

    Class<?> configType();
}
