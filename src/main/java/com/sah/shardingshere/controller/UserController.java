package com.sah.shardingshere.controller;

import com.sah.shardingshere.common.CommonResponse;
import com.sah.shardingshere.entity.SysUser;
import com.sah.shardingshere.security.LoginService;
import com.sah.shardingshere.security.NotAuthentication;
import com.sah.shardingshere.security.model.LoginDTO;
import com.sah.shardingshere.security.model.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @GetMapping("/getUser")
    @NotAuthentication
    public SysUser getUser() {
        SysUser user = new SysUser();
        user.setUsername("test");
        user.setStatus("1");
        user.setCreateTime(new Date());
        user.setMail("1054599027@qq.com");
        user.setTelephone("15659851606");
        return user;
    }

    @PreAuthorize("@ssc.hasPermission('sah:user:query')")
    @PostMapping("/helloWord")
    public String hellWord(){
        return "hello word";
    }

    @PostMapping("/login")
    @NotAuthentication
    @ApiOperation("登录")
    public CommonResponse<LoginVO> login(@RequestBody LoginDTO dto){
        return CommonResponse.ok(loginService.login(dto));
    }


    @PostMapping("/refresh")
    @NotAuthentication
    @ApiOperation("刷新refreshToken")
    public CommonResponse<LoginVO> refreshToken(@RequestHeader(name = "token") String token,
                                        @RequestParam String refreshToken){
        return CommonResponse.ok(loginService.refreshToken(token,refreshToken));
    }

    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }
}
