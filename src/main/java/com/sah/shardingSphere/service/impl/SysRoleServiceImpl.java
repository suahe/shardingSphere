package com.sah.shardingSphere.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingSphere.entity.SysRole;
import com.sah.shardingSphere.mapper.SysRoleMapper;
import com.sah.shardingSphere.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public List<SysRole> queryByUserId(String userId) {
        return baseMapper.queryByUserId(userId);
    }
}
