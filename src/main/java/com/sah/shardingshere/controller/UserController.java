package com.sah.shardingshere.controller;

import com.sah.shardingshere.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author suahe
 * @date 2023/3/27
 * @ApiNote
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @GetMapping("/testSensitive")
    public User testSensitive() {
        User user = new User();
        user.setUsername("test");
        user.setStatus("1");
        user.setCreateTime(new Date());
        user.setEmail("1054599027@qq.com");
        user.setPhone("15659851606");
        return user;
    }

}
