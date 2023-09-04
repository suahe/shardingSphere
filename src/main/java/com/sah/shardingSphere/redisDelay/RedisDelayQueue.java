package com.sah.shardingSphere.redisDelay;

import com.sah.shardingSphere.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * @author suahe
 * @date 2023/9/4 16:57
 */
@Slf4j
@Component
public class RedisDelayQueue extends AbstractRedisDelayQueue {

    @Override
    public void invoke(String jobId) {
        log.info("redis延迟队列: {}执行", RedisDelayQueueEnums.COMMON_REDIS_DELAY_QUEUE.name());
    }

    @Override
    public String setDelayQueueName() {
        return RedisDelayQueueEnums.COMMON_REDIS_DELAY_QUEUE.name();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.init();
    }
}
