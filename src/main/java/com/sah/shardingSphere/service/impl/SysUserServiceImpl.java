package com.sah.shardingSphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingSphere.common.CacheConstant;
import com.sah.shardingSphere.entity.SysUser;
import com.sah.shardingSphere.lock.annotation.JLock;
import com.sah.shardingSphere.mapper.SysUserMapper;
import com.sah.shardingSphere.service.ISysUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    //@Cacheable(value = CacheConstant.SYS_CACHE_USER, key = "#username", unless = "#result == null")
    @Cacheable(cacheNames = CacheConstant.SYS_CACHE_USER, key = "#username")
    public SysUser findByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return this.getOne(wrapper);
    }

    @CacheEvict(value= {CacheConstant.SYS_CACHE_USER}, allEntries=true)
    public boolean editUser(SysUser sysUser) {

        return true;
    }
}
