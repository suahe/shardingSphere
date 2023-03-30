package com.sah.shardingSphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sah.shardingSphere.entity.SysRole;

import java.util.List;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> queryByUserId(String userId);
}
