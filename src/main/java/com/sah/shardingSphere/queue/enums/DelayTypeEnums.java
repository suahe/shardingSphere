package com.sah.shardingSphere.queue.enums;

/**
 * 延时任务类型
 *
 * @author paulG
 * @since 2021/5/7
 */
public enum DelayTypeEnums {

    /**
     * 通用
     */
    COMMON("通用"),
    ;

    private String description;

    DelayTypeEnums(String description) {
        this.description = description;
    }

}
