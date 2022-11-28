package org.tinger.cache.core;

import org.tinger.common.buffer.TingerMapBuffer;
import org.tinger.common.utils.ServiceLoaderUtils;
import org.tinger.core.cache.Cache;
import org.tinger.core.cache.CacheDriver;
import org.tinger.core.cache.CacheProvider;
import org.tinger.core.conf.Config;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TingerCacheProvider implements CacheProvider {

    private final Map<CacheDriver, CacheBuilder> builderMapper;

    private final TingerMapBuffer<CacheDriver, TingerMapBuffer<String, Cache>> buffer = new TingerMapBuffer<>();

    private final Config config;

    public TingerCacheProvider(Config config) {
        this.config = config;
        builderMapper = ServiceLoaderUtils.scan(CacheBuilder.class).stream().collect(Collectors.toMap(CacheBuilder::driver, Function.identity()));
    }

    @Override
    public Cache get(CacheDriver driver, String cacheName) {
        return buffer.get(driver, TingerMapBuffer::new)
                .get(cacheName, () -> {
                    CacheBuilder builder = builderMapper.get(driver);
                    Object config = this.config.load(cacheName, builder.configType());
                    return builder.build(config);
                });
    }
}
