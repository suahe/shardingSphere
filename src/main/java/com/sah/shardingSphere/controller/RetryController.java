package com.sah.shardingSphere.controller;

import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.security.NotAuthentication;
import com.sah.shardingSphere.service.IRetryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suahe
 * @date 2023/8/25 9:29
 */
@Api(value = "retry重试接口", tags = "retry重试接口")
@RequestMapping("/retry")
@RestController
public class RetryController {

    @Autowired
    private IRetryService retryService;

    @PostMapping("/testRetry")
    @ApiOperation("测试重试")
    @NotAuthentication
    public CommonResponse testRetry() {
        try {
            Boolean bo = retryService.testRetry();
            return CommonResponse.ok(bo);
        } catch (Exception e) {
            return CommonResponse.error("重试方法发送错误");
        }
    }
}
