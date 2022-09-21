package com.sah.shardingshere.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author suahe
 * @date 2022/9/21
 * @ApiNote
 */
@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String username;
    private String status;
    private Date createTime;
}
