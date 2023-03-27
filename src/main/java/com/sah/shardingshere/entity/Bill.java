package com.sah.shardingshere.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
@Data
@TableName("t_bill")
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;

    private Integer userId;

    private Long addressId;

    private String status;

    private Date createTime;
}
