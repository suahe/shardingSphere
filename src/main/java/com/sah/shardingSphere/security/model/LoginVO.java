package com.sah.shardingSphere.security.model;

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
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("accessToken")
    private String accessToken;

    @ApiModelProperty("refreshToken")
    private String refreshToken;

    @ApiModelProperty("账号")
    private String username;
}
