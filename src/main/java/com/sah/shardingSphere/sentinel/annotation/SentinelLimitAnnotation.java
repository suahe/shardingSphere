package com.sah.shardingSphere.sentinel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author suahe
 * @date 2023/8/25 9:56
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SentinelLimitAnnotation {

    String resourceName();

    int limitCount() default 5;
}
