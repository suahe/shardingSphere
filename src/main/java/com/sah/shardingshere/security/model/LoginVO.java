package com.sah.shardingshere.security.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Data
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accessToken;

    private String refreshToken;

    private String userName;
}
