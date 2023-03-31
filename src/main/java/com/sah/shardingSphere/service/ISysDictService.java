package com.sah.shardingSphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sah.shardingSphere.entity.SysDict;
import com.sah.shardingSphere.model.DictModel;

import java.util.List;
import java.util.Map;

/**
 * @author suahe
 * @date 2023/3/31
 * @ApiNote
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 通过查询指定table的 text code key 获取字典值，可批量查询
     *
     * @param table
     * @param text
     * @param code
     * @param keys
     * @return
     */
    List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> keys);

    /**
     * 普通字典的翻译，根据多个dictCode和多条数据，多个以逗号分割
     * @param dictCodes 例如：user_status,sex
     * @param keys 例如：1,2,0
     * @return
     */
    Map<String, List<DictModel>> translateManyDict(String dictCodes, String keys);

    /**
     * 可通过多个字典code查询翻译文本
     * @param dictCodeList 多个字典code
     * @param keys 数据列表
     * @return
     */
    Map<String, List<DictModel>> queryManyDictByKeys(List<String> dictCodeList, List<String> keys);

    /**
     * 字典表的 翻译
     * @param table
     * @param text
     * @param code
     * @param key
     * @return
     */
    String translateDictFromTable(String table, String text, String code, String key);

    String queryTableDictTextByKey(String table, String text, String code, String key);

    /**
     * 普通字典的翻译
     * @param code
     * @param key
     * @return
     */
    String translateDict(String code, String key);

    /**
     * 普通字典的翻译
     * @param code
     * @param key
     * @return
     */
    String queryDictTextByKey(String code, String key);

    /**
     * 15 字典表的 翻译，可批量
     * @param table
     * @param text
     * @param code
     * @param keys 多个用逗号分割
     * @return
     */
    List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys);
}
