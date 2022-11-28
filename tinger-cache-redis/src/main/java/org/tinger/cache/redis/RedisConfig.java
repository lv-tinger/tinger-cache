package org.tinger.cache.redis;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RedisConfig {
    private String type;
    private List<RedisHostConfig> items;
}
