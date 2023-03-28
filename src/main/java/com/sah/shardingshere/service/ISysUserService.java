package com.sah.shardingshere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sah.shardingshere.entity.SysUser;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
public interface ISysUserService extends IService<SysUser> {

    SysUser findByUsername(String username);
}
