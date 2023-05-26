package com.sah.shardingSphere.spring.extend;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author suahe
 * @date 2023/5/26 15:32
 */
public class MyFactoryBean implements FactoryBean<MyFactoryBean> {
    @Override
    public MyFactoryBean getObject() throws Exception {
        return new MyFactoryBean();
    }

    @Override
    public Class<?> getObjectType() {
        return MyFactoryBean.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
