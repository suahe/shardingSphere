package com.sah.shardingSphere.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote 自定义数据库分片算法
 */
@Slf4j
public class DBShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        //真实数据库节点
        availableTargetNames.stream().forEach((item) -> {
            log.info("actual db:" + item);
        });
        //逻辑表以及分片的字段名
        log.info("logicTable:" + shardingValue.getLogicTableName() + ";shardingColumn:" + shardingValue.getColumnName());
        //分片数据字段值
        log.info("shardingColumn value:" + shardingValue.getValue().toString());
        //获取字段值
        long orderId = shardingValue.getValue();
        //分片索引计算 0 , 1
        long db_index = orderId & (2 - 1);
        for (String each : availableTargetNames) {
            if (each.equals("ds" + db_index)) {
                //匹配的话，返回数据库名
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

}
