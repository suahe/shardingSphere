package com.sah.shardingSphere.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sah.shardingSphere.entity.SysUser;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 分页查询用户列表
     * @param sysUser
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<SysUser> selectByPage(SysUser sysUser, Integer pageNo, Integer pageSize);

    /**
     * 根据账号查询用户
     *
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 编辑用户信息
     *
     * @return
     */
    boolean editUser(SysUser sysUser);
}
