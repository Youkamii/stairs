package com.sparta.stairs.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void setRefreshToken(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        //delete in 3 hours
        values.set(key, data, Duration.ofHours(3));
    }


    public boolean hasRefreshToken(String key) {
        return redisTemplate.hasKey(key);
    }

    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }

    public void setBlackList(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        //delete in 3 minutes
        values.set(key, data, Duration.ofMinutes(2));
    }

    public boolean hasBlackList(String key) {
        return redisTemplate.hasKey(key);
    }

}