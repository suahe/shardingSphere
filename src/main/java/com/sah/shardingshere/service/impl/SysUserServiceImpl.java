package com.sah.shardingshere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingshere.entity.SysUser;
import com.sah.shardingshere.mapper.SysUserMapper;
import com.sah.shardingshere.service.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser findByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return this.getOne(wrapper);
    }
}
