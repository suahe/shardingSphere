package com.sah.shardingSphere.queue.enums;

/**
 * 队列枚举
 *
 * @author Bulbasaur
 */
public enum DelayQueueEnums {


    /**
     * 通用
     */
    COMMON("通用");

    private String description;

    DelayQueueEnums(String description) {
        this.description = description;
    }
}
