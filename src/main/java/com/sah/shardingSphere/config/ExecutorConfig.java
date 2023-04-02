package com.sah.shardingSphere.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableAsync
@Configuration
public class ExecutorConfig {

    // CPU核数
    public static final int core = Runtime.getRuntime().availableProcessors();

    @Bean(value = "executor")
    public ExecutorService getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(core);
        // 最大线程数
        executor.setMaxPoolSize(core * 2);
        // 除核心线程外线程的存活时间
        executor.setKeepAliveSeconds(50);
        // 值大于0使用LinkedBlockingQueue,否则使用SynchronouseQueue
        executor.setQueueCapacity(500);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        // 线程名称前缀
        executor.setThreadNamePrefix("thread-pool");
        //设置解决策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }
}
