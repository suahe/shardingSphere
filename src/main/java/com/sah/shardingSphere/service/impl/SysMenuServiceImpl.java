package com.sah.shardingSphere.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingSphere.entity.SysMenu;
import com.sah.shardingSphere.mapper.SysMenuMapper;
import com.sah.shardingSphere.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


    @Override
    public List<SysMenu> queryByRoleIds(List<String> roleIds) {
        return baseMapper.queryByRoleIds(roleIds);
    }
}
