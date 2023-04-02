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
        Thread consumer = new Thread() {
            public void run() {
                startConsumer();
            }
        };
        consumer.start();
    }

    public void startConsumer() {
        while (true) {
            //只取一条
            Set<Object> set = redisUtil.rangeByScore(CacheConstant.REDIS_DELAY_QUEUE_TEST, 0, System.currentTimeMillis(), 0, 1);
            if (null != set && set.size() > 0) {
                for (Object v : set) {
                    //获取任务执行的时间
                    long b = redisUtil.zRem(CacheConstant.REDIS_DELAY_QUEUE_TEST, v.toString());
                    if (b > 0) {//分布式抢到任务
                        try {
                            log.info("消费信息：" + v.toString());
                            RedisDelayQueueDTO redisDelayQueueDTO = JSON.parseObject(v.toString(), RedisDelayQueueDTO.class);
                            redisUtil.zRem(CacheConstant.REDIS_DELAY_QUEUE_TEST + redisDelayQueueDTO.getConsumerId(), v.toString());
                        } catch (Exception e) {
                            log.error("redis zRem:{}异常，异常信息:{}", CacheConstant.REDIS_DELAY_QUEUE_TEST, e);
                        }
                    }
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("RedisDelayingQueue startConsumer error:{}", e);
                }
            }
        }
    }
}
