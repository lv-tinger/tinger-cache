package org.tinger.cache.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.kv.UpsertOptions;
import org.tinger.common.codec.TingerTranslator;
import org.tinger.common.codec.Translator;
import org.tinger.core.cache.CacheDriver;
import org.tinger.core.cache.CacheProvider;
import org.tinger.core.func.Call;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * Created by tinger on 2022-11-13
 */
public class CouchbaseProvider implements CacheProvider {
    private final Translator translator = new TingerTranslator();
    private final Cluster cluster;
    private final Bucket bucket;
    private final Collection collection;

    public CouchbaseProvider(Cluster cluster) {
        this.cluster = cluster;
        this.bucket = this.cluster.bucket("tinger-bucket");
        this.collection = this.bucket.defaultCollection();
    }

    public CouchbaseProvider(String server, String username, String password) {
        this(Cluster.connect(server, username, password));
    }

    @Override
    public CacheDriver driver() {
        return CacheDriver.CBS;
    }

    @Override
    public boolean ex(String key) {
        return false;
    }

    @Override
    public boolean nx(String key) {
        return false;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void put(String key, Object value) {
        byte[] bytes = translator.encode(value);
        this.collection.upsert(key, bytes);
    }

    @Override
    public void put(String key, Object value, int expiry) {
        UpsertOptions options = UpsertOptions.upsertOptions().expiry(Duration.of(expiry, ChronoUnit.SECONDS));
        this.put(key, value, options);
    }

    @Override
    public void put(String key, Object value, Date expiry) {
        UpsertOptions options = UpsertOptions.upsertOptions().expiry(Instant.ofEpochMilli(expiry.getTime()));
        this.put(key, value, options);
    }

    private void put(String key, Object value, UpsertOptions options) {
        byte[] bytes = translator.encode(value);
        this.collection.upsert(key, bytes, options);
    }


    @Override
    public void exp(String key, int expiry) {

    }

    @Override
    public void exp(String key, Date date) {

    }

    @Override
    public void del(String key) {

    }

    @Override
    public long incr(String key, long initial, int step, int expiry) {
        return 0;
    }

    @Override
    public long decr(String key, long initial, int step, int expiry) {
        return 0;
    }

    @Override
    public void lock(String key, int timeout, int retry, Call call) {

    }
}
