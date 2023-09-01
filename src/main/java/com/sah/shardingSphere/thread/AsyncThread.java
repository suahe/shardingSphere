package com.sah.shardingSphere.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author suahe
 * @date 2023/5/29 9:33
 */
@Slf4j
public class AsyncThread extends Thread {

    @Override
    public void run() {
        System.out.println("Current thread name:" + Thread.currentThread().getName() + " Send email success!");
    }

    public static void main(String[] args) {
        AsyncThread asyncThread = new AsyncThread();
        asyncThread.run();
        fun();
    }


    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void fun() {
        executorService.submit(() -> log.info("执行业务逻辑..."));
    }
}