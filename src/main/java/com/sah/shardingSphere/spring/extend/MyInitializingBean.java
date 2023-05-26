package com.sah.shardingSphere.spring.extend;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author suahe
 * @date 2023/5/26 15:21
 */
@Data
@Component
public class MyInitializingBean implements InitializingBean {

    private String name;

    @Override
    public void afterPropertiesSet() throws Exception {
        //bean初始化之后被调用
        System.out.println("bean初始化后被调用");
        name = "张三";
    }
}
