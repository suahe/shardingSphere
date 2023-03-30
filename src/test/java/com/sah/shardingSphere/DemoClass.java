package com.sah.shardingSphere;

import lombok.extern.slf4j.Slf4j;

/**
 * @author suahe
 * @date 2023/3/30
 * @ApiNote
 */
@Slf4j
public class DemoClass {

    /**
     * 私有无参构造函数，避免调用方直接new DemoClass()
     */
    public DemoClass() {
    }


    private String filed;

    public String test(String name) {
        log.info("ReflectionClass->name:{}", name);
        return name;
    }
}
