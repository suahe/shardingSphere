package com.sah.shardingshere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sah.shardingshere.entity.SysMenu;
import com.sah.shardingshere.entity.SysUser;

import java.util.List;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据角色ID集合查询菜单列表
     *
     * @param roleIds
     * @return
     */
    List<SysMenu> queryByRoleIds(List<String> roleIds);
}
