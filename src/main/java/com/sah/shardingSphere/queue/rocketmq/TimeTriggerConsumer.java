package com.sah.shardingSphere.queue.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.sah.shardingSphere.queue.model.TimeTriggerMsg;
import com.sah.shardingSphere.queue.trigger.TimeTriggerExecutor;
import com.sah.shardingSphere.queue.util.DelayQueueTools;
import com.sah.shardingSphere.redis.util.RedisUtil;
import com.sah.shardingSphere.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 事件触发消费者
 *
 * @author paulG
 * @since 2020/11/17 7:19 下午
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "${sah.data.rocketmq.common-topic}", consumerGroup = "${sah.data.rocketmq.common-group}")
public class TimeTriggerConsumer implements RocketMQListener<TimeTriggerMsg> {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onMessage(TimeTriggerMsg timeTriggerMsg) {
        try {
            String key = DelayQueueTools.generateKey(timeTriggerMsg.getTriggerExecutor(), timeTriggerMsg.getTriggerTime(), timeTriggerMsg.getUniqueKey());

            if (redisUtil.get(key) == null) {
                log.info("执行器执行被取消：{} | 任务标识：{}", timeTriggerMsg.getTriggerExecutor(), timeTriggerMsg.getUniqueKey());
                return;
            }

            log.info("执行器执行：" + timeTriggerMsg.getTriggerExecutor());
            log.info("执行器参数：" + JSONObject.toJSONString(timeTriggerMsg.getParam()));

            redisUtil.del(key);

            TimeTriggerExecutor executor = (TimeTriggerExecutor) SpringContextUtil.getBean(timeTriggerMsg.getTriggerExecutor());
            executor.execute(timeTriggerMsg.getParam());
        } catch (Exception e) {
            log.error("mq延时任务异常", e);
        }

    }

}
