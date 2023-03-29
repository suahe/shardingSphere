package com.sah.shardingshere.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Data
@ApiModel("登录实体")
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("部门ID")
    private String depId;

    @JsonIgnore
    private Collection<GrantedAuthority> authorities;
}
