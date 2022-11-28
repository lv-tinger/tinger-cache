package org.tinger.cache.redis;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisHostConfig {
    private String host;
    private int port;
}
