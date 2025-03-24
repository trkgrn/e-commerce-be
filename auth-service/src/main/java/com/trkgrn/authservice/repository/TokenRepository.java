package com.trkgrn.authservice.repository;

import com.trkgrn.authservice.model.Token;
import com.trkgrn.common.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class TokenRepository {
    @Value("${jwt.access-token.expiry.seconds}")
    private long ACCESS_TOKEN_EXPIRY;
    @Value("${jwt.refresh-token.expiry.seconds}")
    private long REFRESH_TOKEN_EXPIRY;

    private final StringRedisTemplate redisTemplate;

    public TokenRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public Token save(Token token, Long expiredTime) {
        redisTemplate.opsForValue().set(token.getId(), token.getJwt(), expiredTime, TimeUnit.HOURS);
        return token;
    }

    public Token findTokenById(String id) {
        String jwt = redisTemplate.opsForValue().get(id);
        return new Token(id, jwt);
    }


    public String delete(String username) {
        redisTemplate.opsForValue().getAndDelete(username);
        return "Token removed";
    }

    public void storeAccessToken(String userId, String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(ApplicationConstants.ACCESS_TOKEN_PREFIX + userId, token, ACCESS_TOKEN_EXPIRY, TimeUnit.SECONDS);
    }

    public void storeRefreshToken(String userId, String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(ApplicationConstants.REFRESH_TOKEN_PREFIX + userId, token, REFRESH_TOKEN_EXPIRY, TimeUnit.SECONDS);
    }

    public String getAccessToken(String userId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(ApplicationConstants.ACCESS_TOKEN_PREFIX + userId);
    }

    public String getRefreshToken(String userId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(ApplicationConstants.REFRESH_TOKEN_PREFIX + userId);
    }

    public void deleteAccessToken(String userId) {
        redisTemplate.delete(ApplicationConstants.ACCESS_TOKEN_PREFIX + userId);
    }

    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(ApplicationConstants.REFRESH_TOKEN_PREFIX + userId);
    }

}
