package com.sah.shardingSphere.redis.listener;


import com.sah.shardingSphere.redis.model.BaseMap;

/**
 * 自定义消息监听
 */
public interface RedisListener {

    void onMessage(BaseMap message);

}
