package com.sah.shardingSphere.common;

/**
 * @author suahe
 * @date 2023/3/31
 * @ApiNote
 */
public class CacheConstant {

    /**
     * 用户缓存key
     */
    public static final String SYS_CACHE_USER = "sys:cache:user";

    /**
     * 表字典信息缓存
     */
    public static final String SYS_CACHE_DICT_TABLE = "sys:cache:dictTable";

    /**
     * 字典信息缓存（含禁用的字典项）
     */
    public static final String SYS_CACHE_DICT = "sys:cache:dict";
}
