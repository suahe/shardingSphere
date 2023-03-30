package com.sah.shardingSphere.redis.receiver;


import cn.hutool.core.util.ObjectUtil;
import com.sah.shardingSphere.redis.common.RedisConstant;
import com.sah.shardingSphere.redis.listener.RedisListerer;
import com.sah.shardingSphere.redis.model.BaseMap;
import com.sah.shardingSphere.util.SpringContextUtil;
import lombok.Data;

/**
 * @author zyf
 */
@Data
public class RedisReceiver {


    /**
     * 接受消息并调用业务逻辑处理器
     *
     * @param params
     */
    public void onMessage(BaseMap params) {
        Object handlerName = params.get(RedisConstant.HANDLER_NAME);
        boolean exists = SpringContextUtil.getApplicationContext().containsBean(handlerName.toString());
        if(exists){
            RedisListerer messageListener = SpringContextUtil.getBean(handlerName.toString(), RedisListerer.class);
            if (ObjectUtil.isNotEmpty(messageListener)) {
                messageListener.onMessage(params);
            }
        }
    }

}
