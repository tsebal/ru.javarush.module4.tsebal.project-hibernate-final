package ru.javarush.module4.projecthibernatefinal.utils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisClientProvider {

    private static final Logger logger = LoggerFactory.getLogger(RedisClientProvider.class);
    public static RedisClient prepareRedisClient() {
        RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            logger.info("Connected to Redis DB");
        }
        return redisClient;
    }
}
