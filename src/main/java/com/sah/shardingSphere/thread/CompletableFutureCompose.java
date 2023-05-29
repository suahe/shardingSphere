package com.sah.shardingSphere.thread;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

/**
 * @author suahe
 * @date 2023/5/29 9:15
 */
public class CompletableFutureCompose {

    /**
     * thenAccept子任务和父任务公用同一个线程
     */
    @SneakyThrows
    public static void thenRunAsync() {
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " cf1 do something....");
            return 1;
        });
        CompletableFuture<Integer> cf1After1 = cf1.thenApplyAsync(x -> {
            System.out.println(x.toString());
            return 2;
        });

        CompletableFuture<Void> cf1After2 = cf1.thenRunAsync(() -> {
            System.out.println(Thread.currentThread() + " cf1 after execution....");
        });
        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " cf2 do something...");
            return 2;
        });
        //等待任务1执行完成
        System.out.println("cf1结果->" + cf1.get());
        //等待任务2执行完成
        System.out.println("cf2结果->" + cf2.get());
        System.out.println("cf1执行完后结果->" + cf1After1.get());

    }

    public static void main(String[] args) {
        thenRunAsync();
    }

}
