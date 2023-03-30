package com.sah.shardingSphere.controller;

import com.sah.shardingSphere.aspect.annotation.AutoLog;
import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.entity.SysUser;
import com.sah.shardingSphere.security.LoginService;
import com.sah.shardingSphere.security.NotAuthentication;
import com.sah.shardingSphere.security.model.LoginDTO;
import com.sah.shardingSphere.security.model.LoginVO;
import com.sah.shardingSphere.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public CommonResponse getUser(@PathVariable("username") String username) {
        SysUser sysUser = sysUserService.findByUsername(username);
        return CommonResponse.ok(sysUser);
    }

    @PreAuthorize("@ssc.hasPermission('sah:user:query')")
    @PostMapping("/helloWord")
    public String hellWord() {
        return "hello word";
    }

    @PostMapping("/login")
    @NotAuthentication
    @ApiOperation("登录")
    public CommonResponse<LoginVO> login(@RequestBody LoginDTO dto) {
        return CommonResponse.ok(loginService.login(dto));
    }


    @PostMapping("/refresh")
    @NotAuthentication
    @ApiOperation("刷新refreshToken")
    public CommonResponse<LoginVO> refreshToken(@RequestHeader(name = "accessToken") String accessToken,
                                                @RequestParam String refreshToken) {
        return CommonResponse.ok(loginService.refreshToken(accessToken, refreshToken));
    }

    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }
}
