package com.sah.shardingSphere.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sah.shardingSphere.redis.common.RedisConstant;
import com.sah.shardingSphere.redis.receiver.RedisReceiver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description:
 * @author: yang.yonglian
 * @create: 2021-07-02
 **/
@Configuration
@ConditionalOnClass(ServerEndpointExporter.class)
public class RedisListenerContainerConfig {
    /**
     * redis 监听配置
     *
     * @param redisConnectionFactory redis 配置
     * @return
     */
    @Bean
    @ConditionalOnClass(ServerEndpointExporter.class)
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(commonListenerAdapter(), new ChannelTopic(RedisConstant.REDIS_TOPIC_NAME));
        return container;
    }


    @Bean
    @ConditionalOnClass(ServerEndpointExporter.class)
    MessageListenerAdapter commonListenerAdapter() {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(redisReceiver(), "onMessage");
        messageListenerAdapter.setSerializer(jacksonSerializer());
        return messageListenerAdapter;
    }

    private Jackson2JsonRedisSerializer jacksonSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    @ConditionalOnClass(ServerEndpointExporter.class)
    public RedisReceiver redisReceiver(){
        return new RedisReceiver();
    }
}
