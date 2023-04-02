package com.sah.shardingSphere.schedule;

import com.sah.shardingSphere.lock.annotation.JLock;
import com.sah.shardingSphere.lock.annotation.JRepeat;
import com.sah.shardingSphere.lock.client.RedissonLockClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author suahe
 * @date 2023/3/30
 * @ApiNote
 */
@Slf4j
@Component
public class LockTestSchedule {

    @Autowired
    private RedissonLockClient redissonLockClient;

    @JLock(lockKey= "redis-lock")
    //@JLock(lockKey ={"#user.name","#user.id"})
    //@Scheduled(cron = "0/5 * * * * ?")
    public void testJLock() {
        log.info("执行testJLock任务开始，休眠三秒");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("testJLock执行异常，异常信息：{}", e);
            Thread.currentThread().interrupt();
        }
        System.out.println("=======================业务逻辑1=============================");
        log.info("testJLock任务结束，休眠三秒");
    }

    @JRepeat(lockKey = "JRepeat-lock", lockTime = 5)
    //@Scheduled(cron = "0/5 * * * * ?")
    public void testJRepeat() {
        log.info("执行testJRepeat任务开始，休眠三秒");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("testJRepeat执行异常，异常信息：{}", e);
            Thread.currentThread().interrupt();
        }
        System.out.println("=======================业务逻辑2=============================");
        log.info("testJRepeat任务结束，休眠三秒");
    }


    /**
     * 编码方式测试分布式锁
     */
    //@Scheduled(cron = "0/10 * * * * ?")
    public void execute2() throws InterruptedException {
        if (redissonLockClient.tryLock("redisson", -1, 10000)) {
            log.info("执行任务execute2开始，休眠三秒");
            Thread.sleep(3000);
            System.out.println("=======================业务逻辑2=============================");
            log.info("定时execute2结束，休眠三秒");
            redissonLockClient.unlock("redisson");
        } else {
            log.info("execute2获取锁失败");
        }
    }
}
