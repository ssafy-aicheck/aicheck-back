package com.aicheck.business.domain.auth.infrastructure;

import com.aicheck.business.domain.auth.dto.CheckCodeDto;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthCodeRepository {
    private static final int VERIFICATION_EXPIRE = 60 * 3;
    private static final String VERIFICATION_PREFIX = "verificationEmail:";

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    public void save(final String email, final String randomCode) {
        valueOperations.set(VERIFICATION_PREFIX + email, randomCode);
        redisTemplate.expire(VERIFICATION_PREFIX + email, VERIFICATION_EXPIRE, TimeUnit.SECONDS);
    }

    public void delete(String email) {
        redisTemplate.delete(VERIFICATION_PREFIX + email);
    }

    public String findCodeByEmailAndRandomCode(CheckCodeDto checkCodeDTO) {
        return valueOperations.get(VERIFICATION_PREFIX + checkCodeDTO.getEmail());
    }
}
