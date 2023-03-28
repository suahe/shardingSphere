package com.sah.shardingshere.security;

import com.sah.shardingshere.entity.SysMenu;
import com.sah.shardingshere.entity.SysRole;
import com.sah.shardingshere.entity.SysUser;
import com.sah.shardingshere.security.model.AuthUser;
import com.sah.shardingshere.service.ISysMenuService;
import com.sah.shardingshere.service.ISysRoleService;
import com.sah.shardingshere.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.findByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("未找到用户名");
        }
        List<SysRole> roles = sysRoleService.queryByUserId(sysUser.getId());
        Set<String> dbAuthsSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(roles)) {
            //角色
            roles.forEach(x -> {
                dbAuthsSet.add("ROLE_" + x.getName());
            });
            List<String> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
            List<SysMenu> menus = sysMenuService.queryByRoleIds(roleIds);
            //菜单
            Set<String> permissions = menus.stream().filter(x -> x.getType().equals(3))
                    .map(SysMenu::getPermission).collect(Collectors.toSet());
            dbAuthsSet.addAll(permissions);
        }
        Collection<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        return new AuthUser(username, sysUser.getPassword(), authorities);
    }
}
