package com.sah.shardingSphere.queue.trigger.listen;

import com.sah.shardingSphere.queue.trigger.AbstractDelayQueueListen;
import com.sah.shardingSphere.queue.trigger.TimeTrigger;
import com.alibaba.fastjson.JSONObject;
import com.sah.shardingSphere.queue.enums.DelayQueueEnums;
import com.sah.shardingSphere.queue.model.TimeTriggerMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * @author suahe
 * @date 2023/8/31 14:17
 */
@Slf4j
@Component
public class CommonDelayQueueListen extends AbstractDelayQueueListen {

    @Autowired
    private TimeTrigger timeTrigger;

    @Override
    public void invoke(String jobId) {
        timeTrigger.execute(JSONObject.parseObject(jobId, TimeTriggerMsg.class));
    }

    @Override
    public String setDelayQueueName() {
        return DelayQueueEnums.COMMON.name();
    }

    @Override
    public void run(ApplicationArguments args) {
        this.init();
    }
}
