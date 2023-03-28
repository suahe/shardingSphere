package com.sah.shardingshere.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
public class SecuritySecurityCheckService {


    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    public boolean hasAnyPermissions(String... permissions) {
        if (CollectionUtils.isEmpty(Arrays.asList(permissions))) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).filter(x -> !x.contains("ROLE_"))
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
    }

    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    public boolean hasAnyRoles(String... roles) {
        if (CollectionUtils.isEmpty(Arrays.asList(roles))) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).filter(x -> x.contains("ROLE_"))
                .anyMatch(x -> PatternMatchUtils.simpleMatch(roles, x));
    }
}
