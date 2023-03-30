package com.sah.shardingSphere.redis.client;

import com.sah.shardingSphere.redis.common.RedisConstant;
import com.sah.shardingSphere.redis.model.BaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis客户端
 */
@Configuration
public class RedisClient {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 发送消息
     *
     * @param handlerName
     * @param params
     */
    public void sendMessage(String handlerName, BaseMap params) {
        params.put(RedisConstant.HANDLER_NAME, handlerName);
        redisTemplate.convertAndSend(RedisConstant.REDIS_TOPIC_NAME, params);

    }
}
