package com.sah.shardingSphere.redis.websocket;

import cn.hutool.core.util.ObjectUtil;
import com.sah.shardingSphere.redis.listener.RedisListerer;
import com.sah.shardingSphere.redis.model.BaseMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听消息(采用redis发布订阅方式发送消息)
 */
@Slf4j
@Component
public class SocketHandler implements RedisListerer {

    @Autowired
    private WebSocket webSocket;

    @Override
    public void onMessage(BaseMap map) {
        log.info("【SocketHandler消息】Redis Listerer:" + map.toString());
        String userId = map.get("userId");
        String message = map.get("message");
        if (ObjectUtil.isNotEmpty(userId)) {
            webSocket.pushMessage(userId, message);
        } else {
            webSocket.pushMessage(message);
        }

    }
}
