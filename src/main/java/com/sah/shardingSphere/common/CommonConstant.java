package com.sah.shardingSphere.common;

/**
 * @author suahe
 * @date 2023/3/29
 * @ApiNote
 */
public class CommonConstant {

    /**
     * 系统日志类型： 登录
     */
    public static final int LOG_TYPE_1 = 1;

    /**
     * 系统日志类型： 操作
     */
    public static final int LOG_TYPE_2 = 2;

    /**
     * 操作日志类型： 查询
     */
    public static final int OPERATE_TYPE_1 = 1;

    /**
     * 操作日志类型： 添加
     */
    public static final int OPERATE_TYPE_2 = 2;

    /**
     * 操作日志类型： 更新
     */
    public static final int OPERATE_TYPE_3 = 3;

    /**
     * 操作日志类型： 删除
     */
    public static final int OPERATE_TYPE_4 = 4;

    /**
     * 操作日志类型： 导入
     */
    public static final int OPERATE_TYPE_5 = 5;

    /**
     * 操作日志类型： 导出
     */
    public static final int OPERATE_TYPE_6 = 6;

    /**
     * gateway通过header传递根路径 basePath
     */
    public static final String X_GATEWAY_BASE_PATH = "X_GATEWAY_BASE_PATH";

    /**
     * X_ACCESS_TOKEN
     */
    public static final String X_ACCESS_TOKEN = "X-Access-Token";
}
