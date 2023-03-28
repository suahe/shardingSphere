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

    List<SysMenu> queryByRoleIds(List<String> roleIds);
}
