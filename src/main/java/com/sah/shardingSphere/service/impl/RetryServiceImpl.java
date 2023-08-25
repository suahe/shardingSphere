package com.sah.shardingSphere.service.impl;

import com.sah.shardingSphere.service.IRetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author suahe
 * @date 2023/8/25 9:25
 */
@Slf4j
@Service
public class RetryServiceImpl implements IRetryService {

    @Override
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    public Boolean testRetry() {
        System.out.println("重试...");
        int i = 3 / 0;
        return true;
    }

    @Recover
    public void recover(RuntimeException e) {
        log.error("达到最大重试次数", e);
    }
}
