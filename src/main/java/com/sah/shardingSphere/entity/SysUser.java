package com.sah.shardingSphere.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sah.shardingSphere.serializer.PrivacyEncrypt;
import com.sah.shardingSphere.serializer.PrivacyTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author suahe
 * @date 2022/9/21
 * @ApiNote
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String username;

    @PrivacyEncrypt(type = PrivacyTypeEnum.PHONE)
    private String telephone;

    @PrivacyEncrypt(type = PrivacyTypeEnum.EMAIL)
    private String mail;

    private String password;

    private String depId;

    private String status;

    private String remark;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;
}
