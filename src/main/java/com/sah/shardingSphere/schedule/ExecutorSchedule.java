package com.sah.shardingSphere.schedule;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.collection.SynchronizedCollection;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ExecutorSchedule {

    @Resource(name = "executor")
    private ExecutorService executor;

//    @Scheduled(cron = "0/5 * * * * ?")
    public void executor() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<Future> futureList = new ArrayList<>();
        int length = 10;
        CountDownLatch latch = new CountDownLatch(length);
        for (int i = 0; i < length; i++) {
            int finalI = i;
            Future<?> future = executor.submit(() -> {
                list.add(String.valueOf(finalI));
                latch.countDown();
            });
            futureList.add(future);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("ExecutorSchedule executor error:{}", e);
        }
        List<String> finalList = list.stream().sorted(String::compareTo).collect(Collectors.toList());
        log.info("ExecutorSchedule executor final result finalList:{}, futureList:{}",
                JSONObject.toJSONString(finalList), JSONObject.toJSONString(futureList));
    }

}
