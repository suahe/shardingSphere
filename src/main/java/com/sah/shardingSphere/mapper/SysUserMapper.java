package com.sah.shardingSphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sah.shardingSphere.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> selectByPage(@Param("param") SysUser sysUser, Page<SysUser> page);
}
