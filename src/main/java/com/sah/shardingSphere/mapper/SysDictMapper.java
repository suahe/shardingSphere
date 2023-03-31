package com.sah.shardingSphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sah.shardingSphere.entity.SysDict;
import com.sah.shardingSphere.model.DictModel;
import com.sah.shardingSphere.model.DictModelMany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suahe
 * @date 2023/3/31
 * @ApiNote
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> keys);

    List<DictModelMany> queryManyDictByKeys(@Param("dictCodeList") List<String> dictCodeList, @Param("keys") List<String> keys);

    String queryTableDictTextByKey(@Param("table") String table,@Param("text") String text,@Param("code") String code,@Param("key") String key);

    String queryDictTextByKey(@Param("code") String code,@Param("key") String key);
}
