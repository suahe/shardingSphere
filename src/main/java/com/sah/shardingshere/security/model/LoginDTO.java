package com.sah.shardingshere.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Data
@ApiModel("登录实体")
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
