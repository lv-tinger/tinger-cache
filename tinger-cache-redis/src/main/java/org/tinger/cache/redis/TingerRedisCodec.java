package org.tinger.cache.redis;

import io.lettuce.core.codec.RedisCodec;
import org.tinger.common.codec.TingerTranslator;
import org.tinger.common.codec.Translator;
import org.tinger.common.utils.CodecUtils;

import java.nio.ByteBuffer;

/**
 * Created by tinger on 2022-11-17
 */
public class TingerRedisCodec implements RedisCodec<String, Object> {
    private final Translator translator = new TingerTranslator();

    @Override
    public String decodeKey(ByteBuffer bytes) {
        return CodecUtils.newStringUtf8(bytes.array());
    }

    @Override
    public Object decodeValue(ByteBuffer bytes) {
        return translator.decode(bytes.array());
    }

    @Override
    public ByteBuffer encodeKey(String key) {
        return CodecUtils.getUtf8Buffer(key);
    }

    @Override
    public ByteBuffer encodeValue(Object value) {
        return translator.encodeByteBuff(value);
    }
}
