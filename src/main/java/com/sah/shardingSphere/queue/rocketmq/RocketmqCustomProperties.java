package com.sah.shardingSphere.queue.rocketmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author suahe
 * @date 2023/9/1 17:07
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "sah.data.rocketmq")
public class RocketmqCustomProperties {

    private String commonTopic;

    private String commonGroup;


}