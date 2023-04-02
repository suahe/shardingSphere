package com.sah.shardingSphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingSphere.common.CacheConstant;
import com.sah.shardingSphere.entity.SysUser;
import com.sah.shardingSphere.mapper.SysUserMapper;
import com.sah.shardingSphere.service.ISysUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public IPage<SysUser> selectByPage(SysUser sysUser, Integer pageNo, Integer pageSize) {
        Page<SysUser> page = new Page<>(pageNo, pageSize);
        List<SysUser> sysUserList = baseMapper.selectByPage(sysUser, page);
        page.setRecords(sysUserList);
        return page;
    }

    @Override
    //@Cacheable(value = CacheConstant.SYS_CACHE_USER, key = "#username", unless = "#result == null")
    @Cacheable(cacheNames = CacheConstant.SYS_CACHE_USER, key = "#username")
    public SysUser findByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return this.getOne(wrapper);
    }

    //@CacheEvict(value= {CacheConstant.SYS_CACHE_USER}, allEntries=true)
    @CacheEvict(value = CacheConstant.SYS_CACHE_USER, key = "#sysuser.username", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean editUser(SysUser sysUser) {

        return true;
    }
}
