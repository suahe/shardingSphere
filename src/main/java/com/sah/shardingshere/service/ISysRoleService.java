package com.sah.shardingshere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sah.shardingshere.entity.SysRole;
import com.sah.shardingshere.entity.SysUser;

import java.util.List;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysRole> queryByUserId(String userId);
}
