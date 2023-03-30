package com.sah.shardingSphere.controller;

import com.sah.shardingSphere.aspect.annotation.AutoLog;
import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.entity.SysUser;
import com.sah.shardingSphere.security.LoginService;
import com.sah.shardingSphere.security.NotAuthentication;
import com.sah.shardingSphere.security.model.LoginDTO;
import com.sah.shardingSphere.security.model.LoginVO;
import com.sah.shardingSphere.service.ISysUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author suahe
 * @date 2023/3/27
 * @ApiNote
 */
@Api(value = "用户接口", tags = "用户接口")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ISysUserService sysUserService;

    @AutoLog("根据账号获取用户")
    @GetMapping("/getUser/{username}")
    @NotAuthentication
    @ApiOperation(value="用户接口-获取用户信息")
    public CommonResponse getUser(@ApiParam(name = "账号", value = "username", required = true) @PathVariable("username") String username) {
        SysUser sysUser = sysUserService.findByUsername(username);
        return CommonResponse.ok(sysUser);
    }

    @PreAuthorize("@ssc.hasPermission('sah:user:query')")
    @PostMapping("/helloWord")
    @ApiOperation(value="用户接口-测试权限")
    public String hellWord() {
        return "hello word";
    }

    @PostMapping("/login")
    @NotAuthentication
    @ApiOperation("用户接口-登录")
    public CommonResponse<LoginVO> login(@RequestBody LoginDTO dto) {
        return CommonResponse.ok(loginService.login(dto));
    }


    @PostMapping("/refresh")
    @NotAuthentication
    @ApiOperation("用户接口-刷新refreshToken")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "refreshToken", value = "refreshToken", required = true, dataType = "String", paramType = "query"),
    })
    public CommonResponse<LoginVO> refreshToken(@RequestHeader(name = "accessToken") String accessToken,
                                                @RequestParam("refreshToken") String refreshToken) {
        return CommonResponse.ok(loginService.refreshToken(accessToken, refreshToken));
    }

    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }
}
