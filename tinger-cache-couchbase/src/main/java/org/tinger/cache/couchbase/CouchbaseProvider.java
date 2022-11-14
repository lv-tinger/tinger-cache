package org.tinger.cache.couchbase;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.kv.UpsertOptions;
import org.tinger.common.codec.TingerTranslator;
import org.tinger.common.codec.Translator;
import org.tinger.core.cache.CacheDriver;
import org.tinger.core.cache.CacheProvider;
import org.tinger.core.func.Call;

import java.time.Instant;
import java.util.Date;

/**
 * Created by tinger on 2022-11-13
 */
public class CouchbaseProvider implements CacheProvider {
    private final Translator tingerTranslator = new TingerTranslator();

    private Collection collection;

    public CouchbaseProvider(Collection collection) {
        this.collection = collection;
    }

    @Override
    public CacheDriver driver() {
        return CacheDriver.CBS;
    }

    @Override
    public boolean ex(String s) {
        return collection.exists(s).exists();
    }

    @Override
    public boolean nx(String s) {
        return !ex(s);
    }

    @Override
    public Object get(String s) {
        GetResult result = collection.get(s);
        if (result == null) {
            return null;
        }
        Instant instant = result.expiryTime().orElse(Instant.ofEpochSecond(0));
        if (instant.isBefore(Instant.now())) {
            return null;
        }
        byte[] bytes = result.contentAsBytes();
        return tingerTranslator.decode(bytes);
    }

    @Override
    public void put(String s, Object o) {
        if(o == null){
            MutationResult upsert = collection.upsert(s, o, UpsertOptions.upsertOptions());
            return;
        }


    }

    @Override
    public void put(String s, Object o, int i) {

    }

    @Override
    public void put(String s, Object o, Date date) {

    }

    @Override
    public void exp(String s, int i) {

    }

    @Override
    public void exp(String s, Date date) {

    }

    @Override
    public void del(String s) {

    }

    @Override
    public long incr(String s, long l, int i, int i1) {
        return 0;
    }

    @Override
    public long decr(String s, long l, int i, int i1) {
        return 0;
    }

    @Override
    public void lock(String s, int i, int i1, Call call) {

    }
}
