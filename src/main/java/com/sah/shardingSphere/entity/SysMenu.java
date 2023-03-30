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
@TableName("sys_menu")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String permission;

    private String type;

    private Integer sort;

    private String parentId;

    private String path;

    private String icon;

    private String component;

    private String visible;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;
}
