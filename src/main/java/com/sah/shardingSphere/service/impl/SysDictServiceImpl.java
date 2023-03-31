package com.sah.shardingSphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingSphere.common.CacheConstant;
import com.sah.shardingSphere.common.CommonConstant;
import com.sah.shardingSphere.entity.SysDict;
import com.sah.shardingSphere.entity.SysDictItem;
import com.sah.shardingSphere.mapper.SysDictMapper;
import com.sah.shardingSphere.model.DictModel;
import com.sah.shardingSphere.model.DictModelMany;
import com.sah.shardingSphere.service.ISysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@Service
@Slf4j
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;


    @Override
    public List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> keys) {
        return sysDictMapper.queryTableDictTextByKeys(table, text, code, keys);
    }

    @Override
    public Map<String, List<DictModel>> translateManyDict(String dictCodes, String keys) {
        List<String> dictCodeList = Arrays.asList(dictCodes.split(","));
        List<String> values = Arrays.asList(keys.split(","));
        return this.queryManyDictByKeys(dictCodeList, values);
    }

    @Override
    public Map<String, List<DictModel>> queryManyDictByKeys(List<String> dictCodeList, List<String> keys) {
        List<DictModelMany> list = sysDictMapper.queryManyDictByKeys(dictCodeList, keys);
        Map<String, List<DictModel>> dictMap = new HashMap<>();
        for (DictModelMany dict : list) {
            List<DictModel> dictItemList = dictMap.computeIfAbsent(dict.getDictCode(), i -> new ArrayList<>());
            dictItemList.add(new DictModel(dict.getValue(), dict.getText()));
        }
        return dictMap;
    }

    @Override
    public String translateDictFromTable(String table, String text, String code, String key) {
        return this.queryTableDictTextByKey(table, text, code, key);
    }

    @Override
    @Cacheable(value = CacheConstant.SYS_CACHE_DICT_TABLE, unless = "#result == null ")
    public String queryTableDictTextByKey(String table, String text, String code, String key) {
        log.debug("无缓存dictTable的时候调用这里！");
        return sysDictMapper.queryTableDictTextByKey(table, text, code, key);
    }


    @Override
    public String translateDict(String code, String key) {
        return this.queryDictTextByKey(code, key);
    }

    @Cacheable(value = CacheConstant.SYS_CACHE_DICT, key = "#code+':'+#key", unless = "#result == null ")
    @Override
    public String queryDictTextByKey(String code, String key) {
        log.debug("无缓存dictText的时候调用这里！");
        return sysDictMapper.queryDictTextByKey(code, key);
    }

    @Override
    public List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys) {
        return this.queryTableDictTextByKeys(table, text, code, Arrays.asList(keys.split(",")));
    }
}
