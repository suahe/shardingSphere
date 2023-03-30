package com.sah.shardingshere.config;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author suahe
 * @date 2023/3/29
 * @ApiNote
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
@Configuration
public class RedissonConfig {

    private final String hostPrefix = "redis://";

    private String host;

    private String password;

    private String port;

    private Integer timeout;

    /**
     * Redisson配置
     *
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        //1、创建配置
        Config config = new Config();
        //设置集群方式
        //config.useClusterServers().addNodeAddress()
        host = host.startsWith(hostPrefix) ? host : hostPrefix + host;
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(host + ":" + port)
                .setTimeout(timeout);

        if (StringUtils.isNotBlank(password)) {
            serverConfig.setPassword(password);
        }

        return Redisson.create(config);
    }

}
