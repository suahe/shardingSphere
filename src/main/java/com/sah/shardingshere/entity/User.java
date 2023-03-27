package com.sah.shardingshere.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sah.shardingshere.serializer.PrivacyEncrypt;
import com.sah.shardingshere.serializer.PrivacyTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author suahe
 * @date 2022/9/21
 * @ApiNote
 */
@Data
@TableName("t_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String username;

    private String status;

    private Date createTime;

    @TableField(exist = false)
    @PrivacyEncrypt(type = PrivacyTypeEnum.PHONE) // 隐藏手机号
    private String phone;

    @TableField(exist = false)
    @PrivacyEncrypt(type = PrivacyTypeEnum.EMAIL) // 隐藏邮箱
    private String email;
}
