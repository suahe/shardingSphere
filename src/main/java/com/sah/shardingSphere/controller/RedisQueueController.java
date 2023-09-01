package com.sah.shardingSphere.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sah.shardingSphere.common.CacheConstant;
import com.sah.shardingSphere.queue.model.TimeExecuteConstant;
import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.model.RedisDelayQueueDTO;
import com.sah.shardingSphere.queue.enums.DelayTypeEnums;
import com.sah.shardingSphere.queue.model.TimeTriggerMsg;
import com.sah.shardingSphere.queue.rocketmq.RocketmqCustomProperties;
import com.sah.shardingSphere.queue.trigger.TimeTrigger;
import com.sah.shardingSphere.queue.util.DelayQueueTools;
import com.sah.shardingSphere.redis.util.RedisUtil;
import com.sah.shardingSphere.security.NotAuthentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "redis队列接口", tags = "redis队列接口")
@RequestMapping("/redisQueue")
@RestController
public class RedisQueueController {

    @Autowired
    private RocketmqCustomProperties rocketmqCustomProperties;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TimeTrigger timerTrigger;


    @PostMapping("/addQueue")
    @ApiOperation("添加redis队列")
    @NotAuthentication
    public CommonResponse addQueue(@RequestBody RedisDelayQueueDTO redisDelayQueueDTO) {
        long l = System.currentTimeMillis();
        redisUtil.zAdd(CacheConstant.REDIS_DELAY_QUEUE_TEST, JSON.toJSONString(redisDelayQueueDTO), l);
        boolean bo = redisUtil.zAdd(CacheConstant.REDIS_DELAY_QUEUE_TEST + redisDelayQueueDTO.getConsumerId(), JSON.toJSONString(redisDelayQueueDTO), l);
        if (bo) {
            return CommonResponse.ok();
        }
        return CommonResponse.error("addQueue fail");
    }

    @PostMapping("/addDelayQueue")
    @ApiOperation("添加延迟队列")
    @NotAuthentication
    public CommonResponse addDelayQueue(@RequestBody RedisDelayQueueDTO redisDelayQueueDTO) {
        long l = System.currentTimeMillis();
        JSONObject data = new JSONObject();
        data.put("data", redisDelayQueueDTO.getData());
        TimeTriggerMsg timeTriggerMsg = new TimeTriggerMsg(TimeExecuteConstant.PROMOTION_EXECUTOR,
                l,
                data,
                DelayQueueTools.wrapperUniqueKey(DelayTypeEnums.COMMON, redisDelayQueueDTO.getConsumerId()),
                rocketmqCustomProperties.getCommonTopic());
        timerTrigger.addDelay(timeTriggerMsg);
        return CommonResponse.ok();
    }
}
