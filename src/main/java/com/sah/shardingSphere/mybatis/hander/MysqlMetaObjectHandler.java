package com.sah.shardingSphere.mybatis.hander;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author sundeyao
 */
@Slf4j
@Component
public class MysqlMetaObjectHandler implements MetaObjectHandler {

    /**
     * 测试 user 表 name 字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("*************************");
        log.info("insert of mysql fill");
        log.info("*************************");
        // 测试下划线
        Object createDatetime = this.getFieldValByName("createTime", metaObject);
//        System.out.println("createDatetime=" + createDatetime);
        if (createDatetime == null) {
            //测试实体没有的字段，配置在公共填充，不应该set到实体里面
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }
        Object syStamp = this.getFieldValByName("syStamp", metaObject);
        if (syStamp == null) {
            this.strictInsertFill(metaObject, "syStamp", LocalDateTime.class, LocalDateTime.now());
        }
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("*************************");
        log.info("update of mysql fill");
        log.info("*************************");
//        Object updateTime = this.getFieldValByName("updateTime", metaObject);
//        System.out.println("updateTime=" + updateTime);
//        if (updateTime == null) {
        //测试实体没有的字段，配置在公共填充，不应该set到实体里面
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        Object syStamp = this.getFieldValByName("syStamp", metaObject);
        if (syStamp == null) {
            this.strictInsertFill(metaObject, "syStamp", LocalDateTime.class, LocalDateTime.now());
        }
    }
}
