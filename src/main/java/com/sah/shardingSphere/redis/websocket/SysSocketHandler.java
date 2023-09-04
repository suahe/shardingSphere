package com.sah.shardingSphere.redis.websocket;

import cn.hutool.core.util.ObjectUtil;
import com.sah.shardingSphere.redis.listener.RedisListener;
import com.sah.shardingSphere.redis.model.BaseMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听消息(采用redis发布订阅方式发送消息)
 */
@Slf4j
@Component
public class SysSocketHandler implements RedisListener {

    @Autowired
    private SysWebSocket webSocket;

    @Override
    public void onMessage(BaseMap map) {
        log.info("【SysSocketHandler消息】Redis Listerer:" + map.toString());
        String userId = map.get("userId");
        String message = map.get("message");
        if (ObjectUtil.isNotEmpty(userId)) {
            String[] userIds = userId.split(WebsocketConst.MODEL_SPLIT_CHAR);
            for (String id : userIds) {
                webSocket.sendOneMessage(id, message);
            }
        } else {
            webSocket.sendAllMessage(message);
        }
    }
}
