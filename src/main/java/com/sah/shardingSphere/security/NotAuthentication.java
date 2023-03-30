package com.sah.shardingSphere.security;

import java.lang.annotation.*;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface NotAuthentication {
}
