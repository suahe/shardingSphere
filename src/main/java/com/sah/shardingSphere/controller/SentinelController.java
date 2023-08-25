package com.sah.shardingSphere.controller;

import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.security.NotAuthentication;
import com.sah.shardingSphere.sentinel.annotation.SentinelLimitAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suahe
 * @date 2023/8/25 10:03
 */
@Api(value = "测试限流接口", tags = "测试限流接口")
@RequestMapping("/sentinel")
@RestController
public class SentinelController {

    @PostMapping("/limit")
    @ApiOperation("测试限流")
    @NotAuthentication
    @SentinelLimitAnnotation(limitCount = 1,resourceName = "sentinelLimit")
    public CommonResponse sentinelLimit() {
        return CommonResponse.ok("测试限流");
    }
}
