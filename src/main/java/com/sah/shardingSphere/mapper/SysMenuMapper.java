package com.sah.shardingSphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sah.shardingSphere.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> queryByRoleIds(@Param("roleIds") List<String> roleIds);
}
