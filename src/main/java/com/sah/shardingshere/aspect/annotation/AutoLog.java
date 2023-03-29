package com.sah.shardingshere.aspect.annotation;


import com.sah.shardingshere.common.CommonConstant;
import com.sah.shardingshere.enums.ModuleType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {

    /**
     * 日志内容
     *
     * @return
     */
    String value() default "";

    /**
     * 日志类型
     *
     * @return 0:操作日志;1:登录日志;2:定时任务;
     */
    int logType() default CommonConstant.LOG_TYPE_2;

    /**
     * 操作日志类型
     *
     * @return （1查询，2添加，3修改，4删除）
     */
    int operateType() default 0;

    /**
     * 模块类型 默认为common
     *
     * @return
     */
    ModuleType module() default ModuleType.COMMON;
}
