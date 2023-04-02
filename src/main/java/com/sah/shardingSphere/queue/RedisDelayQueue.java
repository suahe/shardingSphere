package com.sah.shardingSphere.queue;

import com.alibaba.fastjson.JSON;
import com.sah.shardingSphere.common.CacheConstant;
import com.sah.shardingSphere.model.RedisDelayQueueDTO;
import com.sah.shardingSphere.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 *
 */
@Slf4j
@Component
public class RedisDelayQueue {

    @Autowired
    private RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        Thread c = new Thread() {
            public void run() {
                consumer();
            }
        };
        c.start();
    }

    public void consumer() {
        while (true) {
            //只取一条
            Set<Object> set = redisUtil.rangeByScore(CacheConstant.REDIS_DELAY_QUEUE_TEST, 0, System.currentTimeMillis(), 0, 1);
            if (null != set && set.size() > 0) {
                for (Object v : set) {
                    //获取任务执行的时间
                    long b = redisUtil.zRem(CacheConstant.REDIS_DELAY_QUEUE_TEST, v.toString());
                    if (b > 0) {//分布式抢到任务
                        try {
                            log.info("RedisDelayQueue consumer method consumer info：{}", v.toString());
                            RedisDelayQueueDTO redisDelayQueueDTO = JSON.parseObject(v.toString(), RedisDelayQueueDTO.class);
                            redisUtil.zRem(CacheConstant.REDIS_DELAY_QUEUE_TEST + redisDelayQueueDTO.getConsumerId(), v.toString());
                        } catch (Exception e) {
                            log.error("redis zRem:{} error, error info:{}", CacheConstant.REDIS_DELAY_QUEUE_TEST, e);
                        }
                    }
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("RedisDelayingQueue consumer error:{}", e);
                }
            }
        }
    }
}
