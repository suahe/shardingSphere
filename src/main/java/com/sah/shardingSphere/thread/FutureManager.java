package com.sah.shardingSphere.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author suahe
 * @date 2023/5/29 9:31
 */
@Slf4j
public class FutureManager {

    public String execute() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {

                System.out.println(" --- task start --- ");
                Thread.sleep(3000);
                System.out.println(" --- task finish ---");
                return "this is future execute final result!!!";
            }
        });

        //这里需要返回值时会阻塞主线程
        String result = future.get();
        log.info("Future get result: {}", result);
        return result;
    }

    @SneakyThrows
    public static void main(String[] args) {
        FutureManager manager = new FutureManager();
        manager.execute();
    }
}
