package com.sah.shardingSphere.redisDelay;

/**
 * @author suahe
 * @date 2023/9/4 16:59
 */
public enum RedisDelayQueueEnums {
    /**
     * 通用
     */
    COMMON_REDIS_DELAY_QUEUE("通用redis延迟队列");

    private String description;

    RedisDelayQueueEnums(String description) {
        this.description = description;
    }

}
