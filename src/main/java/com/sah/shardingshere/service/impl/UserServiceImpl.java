package com.sah.shardingshere.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingshere.entity.User;
import com.sah.shardingshere.mapper.UserMapper;
import com.sah.shardingshere.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
