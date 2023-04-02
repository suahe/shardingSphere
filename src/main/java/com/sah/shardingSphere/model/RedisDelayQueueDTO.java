package com.sah.shardingSphere.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("redis队列实体")
public class RedisDelayQueueDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    @ApiModelProperty("消费ID")
    private String consumerId;

    /**
     * 数据
     */
    @ApiModelProperty("消费数据")
    private String data;
}
