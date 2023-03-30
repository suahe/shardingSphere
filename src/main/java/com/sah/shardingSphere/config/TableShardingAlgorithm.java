package com.sah.shardingSphere.config;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote 表按日期自定义分片
 */
@Slf4j
public class TableShardingAlgorithm implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {

    private static final String YYYY_MM = "yyyy_MM";

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        //真实数据库节点
        availableTargetNames.stream().forEach((item) -> {
            log.info("actual db:" + item);
        });
        //逻辑表以及分片的字段名
        log.info("logicTable:" + shardingValue.getLogicTableName() + ";shardingColumn:" + shardingValue.getColumnName());
        //分片数据字段值
        log.info("shardingColumn value:" + shardingValue.getValue().toString());
        //获取表名前缀
        String tb_name = shardingValue.getLogicTableName() + "_";
        //根据日期分表
        Date date = shardingValue.getValue();
        String year = String.format("%tY", date);
        String month = String.valueOf(Integer.parseInt(String.format("%tm", date)));
        //String dat = String.format("%td", date); //也可以安装年月日来分表
        // 选择表
        tb_name = tb_name + year + "_" + month;
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

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        log.info("RangeShardingAlgorithm->availableTargetNames={}", JSONObject.toJSONString(availableTargetNames));
        log.info("RangeShardingAlgorithm->rangeShardingValue={}", JSONObject.toJSONString(rangeShardingValue));
        List<String> list = new ArrayList<>();
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        //获取上限,下限
        Date lowerDate = valueRange.lowerEndpoint();
        Date upperDate = valueRange.upperEndpoint();
        //获取分片键的日期格式为"2020_12"
        String lowerSuffix = DateUtil.format(lowerDate, YYYY_MM);
        String upperSuffix;
        if (upperDate != null) {
            upperSuffix = DateUtil.format(upperDate, YYYY_MM);
        } else {
            upperSuffix = DateUtil.format(new Date(), YYYY_MM);
        }
        Set<String> suffixList = getSuffixListForRange(lowerSuffix, upperSuffix);
        for (String tableName : availableTargetNames) {
            if (containTableName(suffixList, tableName)) {
                list.add(tableName);
            }
        }
        return list;
    }

    public static Set<String> getSuffixListForRange(String lowerSuffix, String upperSuffix) {
        Set<String> suffixList = new TreeSet<>();
        if (lowerSuffix.equals(upperSuffix)) {
            //上下界在同一张表
            suffixList.add(lowerSuffix);
        } else {
            //上下界不在同一张表  计算间隔的所有表
            String tempSuffix = lowerSuffix;
            while (!tempSuffix.equals(upperSuffix)) {
                suffixList.add(tempSuffix);
                Date tempDate = DateUtil.parse(tempSuffix, YYYY_MM);
                tempSuffix = DateUtil.format(DateUtil.offsetMonth(tempDate, 1), YYYY_MM);
            }
            suffixList.add(tempSuffix);
        }

        suffixList = suffixList.stream().map(s -> {
            Date date = DateUtil.parse(s, YYYY_MM);
            String year = String.format("%tY", date);
            String month = String.valueOf(Integer.parseInt(String.format("%tm", date)));
            return year + "_" + month;
        }).collect(Collectors.toSet());
        return suffixList;
    }

    private boolean containTableName(Set<String> suffixList, String tableName) {
        boolean flag = false;
        for (String s : suffixList) {
            if (tableName.endsWith(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}

