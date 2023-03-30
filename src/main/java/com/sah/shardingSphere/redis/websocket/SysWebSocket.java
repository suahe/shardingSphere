package com.sah.shardingSphere.redis.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/backstage/{userId}")
public class SysWebSocket {

    private static final String REDIS_TOPIC_NAME = "sysSocketHandler";
    private Session session;
    private static CopyOnWriteArraySet<SysWebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, List<Session>> sessionPool = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            this.session = session;
            webSockets.add(this);
            List<Session> sessions = sessionPool.get(userId);
            if (sessions == null) {
                sessions = new ArrayList<>();
            }
            sessions.add(session);
            sessionPool.put(userId, sessions);
            log.debug("【websocket消息】有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            Set<Map.Entry<String, List<Session>>> entrySet = sessionPool.entrySet();
            Iterator<Map.Entry<String, List<Session>>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Session>> entry = iterator.next();
                String key = entry.getKey();
                List<Session> values = entry.getValue();
                for (int i = 0; i < values.size(); i++) {
                    if (this.session.getId().equals(values.get(i).getId())) {
                        values.remove(i);
                    }
                }
                if (values.size() > 0) {
                    sessionPool.put(key, values);
                } else {
                    iterator.remove();
                }
                return;
            }
            log.debug("【websocket消息】连接断开，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    @OnMessage
    public void onMessage(String message) {

    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.debug("【websocket消息】广播消息:" + message);
        for (SysWebSocket webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        List<Session> sessions = sessionPool.get(userId);
        if (sessions == null) {
            return;
        }
        for (Session session : sessions) {
            if (session != null && session.isOpen()) {
                try {
                    log.debug("【websocket消息】 单点消息:" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            List<Session> sessions = sessionPool.get(userId);
            if (sessions == null) {
                return;
            }
            for (Session session : sessions) {
                if (session != null && session.isOpen()) {
                    try {
                        log.debug("【websocket消息】 单点消息:" + message);
                        session.getAsyncRemote().sendText(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
