package com.sah.shardingSphere.queue.trigger.executor;

import com.alibaba.fastjson.JSONObject;
import com.sah.shardingSphere.queue.model.TimeExecuteConstant;
import com.sah.shardingSphere.queue.trigger.TimeTriggerExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 促销事件触发
 *
 * @author Chopper
 * @version v4.1
 * @since 2020/11/17 7:20 下午
 */
@Slf4j
@Component(TimeExecuteConstant.PROMOTION_EXECUTOR)
public class PromotionTimeTriggerExecutor implements TimeTriggerExecutor {

    @Override
    public void execute(Object message) {
        log.info("PromotionTimeTriggerExecutor execute: {}", JSONObject.toJSONString(message));
    }

}
