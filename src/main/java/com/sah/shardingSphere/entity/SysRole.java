package com.sah.shardingSphere.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String code;

    private String status;

    private String remark;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;
}
