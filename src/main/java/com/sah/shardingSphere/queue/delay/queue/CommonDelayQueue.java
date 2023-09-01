package com.sah.shardingSphere.queue.delay.queue;

import com.sah.shardingSphere.queue.delay.AbstractDelayQueueMachineFactory;
import com.sah.shardingSphere.queue.enums.DelayQueueEnums;
import org.springframework.stereotype.Component;

/**
 * 促销延迟队列
 *
 * @author paulG
 * @version v4.1
 * @since 2020/11/17 7:19 下午
 * @since 1
 */
@Component
public class CommonDelayQueue extends AbstractDelayQueueMachineFactory {


    @Override
    public String setDelayQueueName() {
        return DelayQueueEnums.COMMON.name();
    }
}
