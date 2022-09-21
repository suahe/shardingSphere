package com.sah.shardingshere.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote 表按日期自定义分片
 */
@Slf4j
public class TableShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        //真实数据库节点
        availableTargetNames.stream().forEach((item) -> {
            log.info("actual db:" + item);
        });
        //逻辑表以及分片的字段名
        log.info("logicTable:"+shardingValue.getLogicTableName()+";shardingColumn:"+ shardingValue.getColumnName());
        //分片数据字段值
        log.info("shardingColumn value:"+ shardingValue.getValue().toString());
        //获取表名前缀
        String tb_name = shardingValue.getLogicTableName() + "_";
        //根据日期分表
        Date date = shardingValue.getValue();
        String year = String.format("%tY", date);
        String mon =String.valueOf(Integer.parseInt(String.format("%tm", date)));
        //String dat = String.format("%td", date); //也可以安装年月日来分表
        // 选择表
        tb_name = tb_name + year + "_" + mon;
        //实际的表名
        log.info("tb_name:" + tb_name);
        for (String each : availableTargetNames) {
            //log.info("availableTableName:" + each);
            if (each.equals(tb_name)) {
                //返回物理表名
                return each;
            }
        }
        throw new IllegalArgumentException();
    }
}

