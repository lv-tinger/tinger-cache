package org.tinger.cache.core;

import org.tinger.core.apps.ApplicationWeaverModule;
import org.tinger.core.apps.Provider;
import org.tinger.core.apps.provider.SimpleProvider;
import org.tinger.core.cache.CacheModule;
import org.tinger.core.cache.CacheProvider;
import org.tinger.core.conf.ConfigModule;

public class TingerCacheModule extends ApplicationWeaverModule implements CacheModule {
    private CacheProvider provider;

    @Override
    public void install() {
        provider = new TingerCacheProvider(this.application.module(ConfigModule.class).provide().provide());
    }

    @Override
    public void destroy() {

    }

    @Override
    public Provider<CacheProvider> provide() {
        return new SimpleProvider<>(provider);
    }
}
