package com.sah.shardingSphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sah.shardingSphere.entity.SysRole;

import java.util.List;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId
     * @return
     */
    List<SysRole> queryByUserId(String userId);
}
